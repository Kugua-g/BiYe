package com.example.biye.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.biye.model.Package;
import com.example.biye.model.UserCredential;
import com.example.biye.model.UserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseManager";
    private static final String DATABASE_NAME = "wuliu.db";
    private static final int DATABASE_VERSION = 6;

    public interface Callback<T> {
        void onResult(T result);
    }

    public interface LoginCallback {
        void onResult(String userId, String userType, boolean success);
    }

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        db.execSQL(
            "CREATE TABLE users (" +
            "user_id TEXT PRIMARY KEY," +
            "username TEXT NOT NULL," +
            "phone TEXT," +
            "email TEXT," +
            "user_type TEXT NOT NULL DEFAULT 'user'" +
            ")"
        );

        // 创建用户凭证表
        db.execSQL(
            "CREATE TABLE user_credentials (" +
            "user_id TEXT PRIMARY KEY," +
            "username TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "user_type TEXT NOT NULL DEFAULT 'user'," +
            "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
            ")"
        );

        // 创建包裹表
        db.execSQL(
            "CREATE TABLE packages (" +
            "package_id TEXT PRIMARY KEY," +
            "sender_id TEXT NOT NULL," +
            "courier_id TEXT," +
            "status TEXT NOT NULL," +
            "weight REAL NOT NULL," +
            "volume REAL," +
            "shipping_cost REAL NOT NULL," +
            "sent_time INTEGER NOT NULL," +
            "estimated_arrival_time INTEGER," +
            "actual_arrival_time INTEGER," +
            "receiver_name TEXT NOT NULL," +
            "receiver_phone TEXT NOT NULL," +
            "receiver_address TEXT NOT NULL," +
            "pickup_code TEXT NOT NULL," +
            "FOREIGN KEY(sender_id) REFERENCES users(user_id)" +
            ")"
        );

        // 创建默认管理员账户
        try {
            // 先创建管理员用户记录
            ContentValues adminValues = new ContentValues();
            adminValues.put("user_id", "admin");
            adminValues.put("username", "admin");
            adminValues.put("phone", "");
            adminValues.put("email", "");
            adminValues.put("user_type", UserCredential.TYPE_ADMIN);
            long result = db.insert("users", null, adminValues);
            
            if (result != -1) {
                // 创建管理员凭证
                ContentValues adminCredValues = new ContentValues();
                adminCredValues.put("user_id", "admin");
                adminCredValues.put("username", "admin");
                adminCredValues.put("password", "admin123");
                adminCredValues.put("user_type", UserCredential.TYPE_ADMIN);
                db.insert("user_credentials", null, adminCredValues);
            }
            
            Log.d(TAG, "Admin account created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error creating admin account: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        // 备份现有数据
        List<UserCredential> credentials = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT user_id, username, password FROM user_credentials", null);
            while (cursor.moveToNext()) {
                UserCredential cred = new UserCredential();
                cred.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
                cred.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                cred.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                cred.setUserType(UserCredential.TYPE_USER);  // Set default type
                credentials.add(cred);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error backing up credentials: " + e.getMessage());
        }

        // 删除旧表
        db.execSQL("DROP TABLE IF EXISTS user_credentials");
        db.execSQL("DROP TABLE IF EXISTS users");

        // 创建新表
        db.execSQL(
            "CREATE TABLE users (" +
            "user_id TEXT PRIMARY KEY," +
            "username TEXT NOT NULL," +
            "phone TEXT," +
            "email TEXT," +
            "user_type TEXT NOT NULL DEFAULT 'user'" +
            ")"
        );

        db.execSQL(
            "CREATE TABLE user_credentials (" +
            "user_id TEXT PRIMARY KEY," +
            "username TEXT NOT NULL," +
            "password TEXT NOT NULL," +
            "user_type TEXT NOT NULL DEFAULT 'user'," +
            "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
            ")"
        );

        // 恢复用户数据
        for (UserCredential cred : credentials) {
            // 创建用户记录
            ContentValues userValues = new ContentValues();
            userValues.put("user_id", cred.getUserId());
            userValues.put("username", cred.getUsername());
            userValues.put("user_type", cred.getUserType());
            db.insert("users", null, userValues);

            // 创建凭证记录
            ContentValues credValues = new ContentValues();
            credValues.put("user_id", cred.getUserId());
            credValues.put("username", cred.getUsername());
            credValues.put("password", cred.getPassword());
            credValues.put("user_type", cred.getUserType());
            db.insert("user_credentials", null, credValues);
        }

        // 创建管理员账户
        try {
            ContentValues adminValues = new ContentValues();
            adminValues.put("user_id", "admin");
            adminValues.put("username", "admin");
            adminValues.put("phone", "");
            adminValues.put("email", "");
            adminValues.put("user_type", UserCredential.TYPE_ADMIN);
            db.insert("users", null, adminValues);
        //sb俄罗斯人，这俄罗斯怎么还不④，鸡蛋里面挑骨头，真是sb国家养的b🐏的人
            ContentValues adminCredValues = new ContentValues();
            adminCredValues.put("user_id", "admin");
            adminCredValues.put("username", "admin");
            adminCredValues.put("password", "admin123");
            adminCredValues.put("user_type", UserCredential.TYPE_ADMIN);
            db.insert("user_credentials", null, adminCredValues);
            
            Log.d(TAG, "Admin account created during upgrade");
        } catch (Exception e) {
            Log.e(TAG, "Error creating admin account during upgrade: " + e.getMessage());
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void registerUser(String userId, String username, String password, String phone, String email, Callback<Boolean> callback) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // 插入用户基本信息
            ContentValues userValues = new ContentValues();
            userValues.put("user_id", userId);
            userValues.put("username", username);
            userValues.put("phone", phone);
            userValues.put("email", email);
            userValues.put("user_type", UserCredential.TYPE_USER);  // 设置为普通用户
            db.insert("users", null, userValues);

            // 插入用户登录凭证
            ContentValues credValues = new ContentValues();
            credValues.put("user_id", userId);
            credValues.put("username", username);
            credValues.put("password", password);
            credValues.put("user_type", UserCredential.TYPE_USER);  // 设置为普通用户
            db.insert("user_credentials", null, credValues);

            db.setTransactionSuccessful();
            callback.onResult(true);
        } catch (Exception e) {
            Log.e(TAG, "Error registering user: " + e.getMessage());
            callback.onResult(false);
        } finally {
            db.endTransaction();
        }
    }

    public void addPackage(Package pkg, Callback<Boolean> callback) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // 生成6位随机取件码
            String pickupCode = String.format("%06d", new Random().nextInt(1000000));
            pkg.setPickupCode(pickupCode);

            // 准备包裹数据
            ContentValues values = new ContentValues();
            values.put("package_id", pkg.getPackageId());
            values.put("sender_id", pkg.getSenderId());
            values.put("status", "Pending");  // 初始状态为待取件
            values.put("weight", pkg.getWeight());
            values.put("volume", pkg.getVolume());
            values.put("shipping_cost", pkg.getShippingCost());
            values.put("sent_time", pkg.getSentTime());
            values.put("estimated_arrival_time", pkg.getEstimatedArrivalTime());
            values.put("receiver_name", pkg.getReceiverName());
            values.put("receiver_phone", pkg.getReceiverPhone());
            values.put("receiver_address", pkg.getReceiverAddress());
            values.put("pickup_code", pkg.getPickupCode());

            // 插入数据库
            long result = db.insert("packages", null, values);
            callback.onResult(result != -1);
        } catch (Exception e) {
            Log.e(TAG, "Error adding package: " + e.getMessage());
            callback.onResult(false);
        }
    }

    public void getPackages(String userId, Callback<List<Package>> callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Package> packages = new ArrayList<>();
        try {
            String query;
            String[] args;
            boolean isAdmin = isUserAdmin(userId);
            
            if (isAdmin) {
                // 管理员可以看到所有包裹
                query = "SELECT * FROM packages ORDER BY sent_time DESC";
                args = null;
            } else {
                // 普通用户只能看到自己的包裹
                query = "SELECT * FROM packages WHERE sender_id = ? ORDER BY sent_time DESC";
                args = new String[]{userId};
            }
            
            // 执行查询
            Cursor cursor = db.rawQuery(query, args);
            while (cursor.moveToNext()) {
                packages.add(createPackageFromCursor(cursor));
            }
            cursor.close();
            callback.onResult(packages);
        } catch (Exception e) {
            Log.e(TAG, "Error getting packages: " + e.getMessage());
            callback.onResult(new ArrayList<>());
        }
    }

    public void getRecentPackages(String userId, Callback<List<Package>> callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<Package> packages = new ArrayList<>();
        try {
            String query;
            String[] args;
            boolean isAdmin = isUserAdmin(userId);
            
            if (isAdmin) {
                // Admin can see all recent packages
                query = "SELECT * FROM packages ORDER BY sent_time DESC LIMIT 5";
                args = null;
            } else {
                // Regular users can only see their own packages
                query = "SELECT * FROM packages WHERE sender_id = ? ORDER BY sent_time DESC LIMIT 5";
                args = new String[]{userId};
            }
            
            Cursor cursor = db.rawQuery(query, args);
            while (cursor.moveToNext()) {
                packages.add(createPackageFromCursor(cursor));
            }
            cursor.close();
            callback.onResult(packages);
        } catch (Exception e) {
            Log.e(TAG, "Error getting recent packages: " + e.getMessage());
            callback.onResult(new ArrayList<>());
        }
    }

    public void getPackageDetails(String packageId, Callback<Package> callback) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                "SELECT * FROM packages WHERE package_id = ?",
                new String[]{packageId}
            );
            if (cursor.moveToFirst()) {
                callback.onResult(createPackageFromCursor(cursor));
            } else {
                callback.onResult(null);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error getting package details: " + e.getMessage());
            callback.onResult(null);
        }
    }

    public void pickupPackage(String code, Callback<Boolean> callback) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("status", "Delivered");
            
            int rows = db.update("packages", 
                values, 
                "pickup_code = ? AND status = ?", 
                new String[]{code, "Pending"});
            
            callback.onResult(rows > 0);
        } catch (Exception e) {
            Log.e(TAG, "Error picking up package: " + e.getMessage());
            callback.onResult(false);
        }
    }

    private Package createPackageFromCursor(Cursor cursor) {
        Package pkg = new Package();
        pkg.setPackageId(cursor.getString(cursor.getColumnIndex("package_id")));
        pkg.setSenderId(cursor.getString(cursor.getColumnIndex("sender_id")));
        pkg.setCourierId(cursor.getString(cursor.getColumnIndex("courier_id")));
        pkg.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        pkg.setWeight(cursor.getDouble(cursor.getColumnIndex("weight")));
        pkg.setVolume(cursor.getDouble(cursor.getColumnIndex("volume")));
        pkg.setShippingCost(cursor.getDouble(cursor.getColumnIndex("shipping_cost")));
        pkg.setSentTime(cursor.getLong(cursor.getColumnIndex("sent_time")));
        pkg.setEstimatedArrivalTime(cursor.getLong(cursor.getColumnIndex("estimated_arrival_time")));
        pkg.setActualArrivalTime(cursor.getLong(cursor.getColumnIndex("actual_arrival_time")));
        pkg.setReceiverName(cursor.getString(cursor.getColumnIndex("receiver_name")));
        pkg.setReceiverPhone(cursor.getString(cursor.getColumnIndex("receiver_phone")));
        pkg.setReceiverAddress(cursor.getString(cursor.getColumnIndex("receiver_address")));
        
        // Safely get pickup code
        int pickupCodeIndex = cursor.getColumnIndex("pickup_code");
        if (pickupCodeIndex != -1) {
            pkg.setPickupCode(cursor.getString(pickupCodeIndex));
        }
        
        return pkg;
    }

    public void validateUser(String username, String password, LoginCallback callback) {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Log.d(TAG, "Attempting to validate user: " + username);
            
            Cursor cursor = db.rawQuery(
                "SELECT user_id, user_type FROM user_credentials WHERE username = ? AND password = ?",
                new String[]{username, password}
            );
            
            if (cursor.moveToFirst()) {
                String userId = cursor.getString(cursor.getColumnIndex("user_id"));
                String userType = cursor.getString(cursor.getColumnIndex("user_type"));
                Log.d(TAG, "User found - ID: " + userId + ", Type: " + userType);
                callback.onResult(userId, userType, true);
            } else {
                Log.d(TAG, "User not found or invalid credentials");
                // Check if username exists
                Cursor userCheck = db.rawQuery(
                    "SELECT username FROM user_credentials WHERE username = ?",
                    new String[]{username}
                );
                if (userCheck.moveToFirst()) {
                    Log.d(TAG, "Username exists but password is incorrect");
                } else {
                    Log.d(TAG, "Username does not exist");
                }
                userCheck.close();
                callback.onResult(null, null, false);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error validating user: " + e.getMessage());
            callback.onResult(null, null, false);
        }
    }

    public void checkAdminAccount() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(
                "SELECT * FROM user_credentials WHERE username = ? AND user_type = ?",
                new String[]{"admin", UserCredential.TYPE_ADMIN}
            );
            
            if (!cursor.moveToFirst()) {
                Log.d(TAG, "Admin account not found, creating...");
                // Create admin account
                db = getWritableDatabase();
                ContentValues adminValues = new ContentValues();
                adminValues.put("user_id", "admin");
                adminValues.put("username", "admin");
                adminValues.put("user_type", UserCredential.TYPE_ADMIN);
                db.insert("users", null, adminValues);

                ContentValues adminCredValues = new ContentValues();
                adminCredValues.put("user_id", "admin");
                adminCredValues.put("username", "admin");
                adminCredValues.put("password", "admin123");
                adminCredValues.put("user_type", UserCredential.TYPE_ADMIN);
                db.insert("user_credentials", null, adminCredValues);
                
                Log.d(TAG, "Admin account created");
            } else {
                Log.d(TAG, "Admin account exists");
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error checking admin account: " + e.getMessage());
        }
    }

    // Add helper method to check if user is admin
    private boolean isUserAdmin(String userId) {
        try {
            Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT user_type FROM users WHERE user_id = ?",
                new String[]{userId}
            );
            if (cursor.moveToFirst()) {
                String userType = cursor.getString(cursor.getColumnIndex("user_type"));
                cursor.close();
                return UserCredential.TYPE_ADMIN.equals(userType);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error checking admin status: " + e.getMessage());
        }
        return false;
    }

    public void getAllUsers(Callback<List<UserInfo>> callback) {
        SQLiteDatabase db = getReadableDatabase();
        List<UserInfo> users = new ArrayList<>();
        try {
            // Join query user table and package table, count packages for each user
            String query = 
                "SELECT u.*, " +
                "(SELECT COUNT(*) FROM packages p WHERE p.sender_id = u.user_id) as package_count " +
                "FROM users u " +
                "ORDER BY u.username";
                
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                UserInfo user = new UserInfo();
                user.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setUserType(cursor.getString(cursor.getColumnIndex("user_type")));
                user.setPackageCount(cursor.getInt(cursor.getColumnIndex("package_count")));
                users.add(user);
            }
            cursor.close();
            callback.onResult(users);
        } catch (Exception e) {
            Log.e(TAG, "Error getting users: " + e.getMessage());
            callback.onResult(new ArrayList<>());
        }
    }

    // ... Other methods similar to modify, use SQLite API ...
} 