package com.app.grabnews.util

import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.app.grabnews.R


class ManageFragment {

    companion object {

        /**
         * Replace the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun replaceFrag(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Replace Fragment $e")
            }

        }

        /**
         * Add the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun addFrag(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Add Fragment $e")
            }

        }

        /**
         * To remove a fragments from stack
         */
        fun removeFragment(activity: FragmentActivity) {
            activity.supportFragmentManager.popBackStack()
        }

    }
}
