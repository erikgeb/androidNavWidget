package com.geb.navwithwidget

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private var mSelectedScreen = R.id.icon_apple
    private lateinit var menuButtonColors: ColorStateList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuButtonColors = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed)
            ), intArrayOf(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent)
            )
        )

        setContentView(R.layout.activity_main)

        setMenuButtonListener(R.id.icon_apple)
        setMenuButtonListener(R.id.icon_android)
        setMenuButtonListener(R.id.icon_react)

        val navController = findNavController(R.id.navigationFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.blankFragment -> switchSelectedScreen(R.id.icon_apple)
                R.id.itemFragment -> switchSelectedScreen(R.id.icon_android)
                R.id.plusOneFragment -> switchSelectedScreen(R.id.icon_react)
            }
        }
    }

    private fun setMenuButtonListener(item: Int) {
        val menuButton = findViewById<ImageButton>(item)
        menuButton.setOnClickListener {
            val targetId = when (item) {
                R.id.icon_apple -> R.id.blankFragment
                R.id.icon_android -> R.id.itemFragment
                R.id.icon_react -> R.id.plusOneFragment
                else -> R.id.blankFragment
            }
            findNavController(R.id.navigationFragment).navigate(targetId)
        }
    }

    private fun switchSelectedScreen(newlySelectedScreen: Int) {
        val previousMenuItem = findViewById<ImageButton>(mSelectedScreen)
        val selectedMenuItem = findViewById<ImageButton>(newlySelectedScreen)

        if (previousMenuItem != selectedMenuItem) {
            previousMenuItem.imageTintList = null
            previousMenuItem.isEnabled = true
        }

        selectedMenuItem.imageTintMode = PorterDuff.Mode.SRC_ATOP
        selectedMenuItem.imageTintList = menuButtonColors
        selectedMenuItem.isEnabled = false
        mSelectedScreen = newlySelectedScreen
    }
}
