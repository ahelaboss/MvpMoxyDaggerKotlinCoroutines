package com.yourgains.mvpmoxydaggertemplate.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import java.lang.ref.WeakReference

object AndroidNavigationUI {

    fun setupWithNavController(
        navigationView: NavigationView,
        navController: NavController,
        listener: NavigationView.OnNavigationItemSelectedListener? = null
    ) {
        navigationView.setNavigationItemSelectedListener { item ->
            val handled = if (listener?.onNavigationItemSelected(item) == true) false
            else NavigationUI.onNavDestinationSelected(item, navController)
            if (handled) {
                val parent = navigationView.parent
                if (parent is DrawerLayout) {
                    parent.closeDrawer(navigationView)
                }
            }

            handled
        }
        val weakReference = WeakReference(navigationView)
        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                } else {
                    val menu = view.menu
                    var h = 0

                    val size = menu.size()
                    while (h < size) {
                        val item = menu.getItem(h)
                        item.isChecked = matchDestination(destination, item.itemId)
                        ++h
                    }

                }
            }
        })
    }

    internal fun matchDestination(destination: NavDestination, @IdRes destId: Int): Boolean {
        var currentDestination: NavDestination? = destination
        while (currentDestination!!.id != destId && currentDestination.parent != null) {
            currentDestination = currentDestination.parent
        }
        return currentDestination.id == destId
    }
}