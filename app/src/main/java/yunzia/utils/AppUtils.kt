package yunzia.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log

class AppUtils(context: Context) {
    val mContext  = context
    fun isAppInstalled(PackageName : String  ):Boolean {
        val packageManager = mContext.packageManager;
        try {
            val info = packageManager.getPackageInfo(PackageName, 0);
            return info!=null
        } catch (e: PackageManager.NameNotFoundException ) {
            Log.e("ggc", "isAppInstalled: ", e)

        }
        return false;
    }
}