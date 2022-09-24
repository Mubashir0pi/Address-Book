package com.assesment.addressbook.util

import androidx.navigation.navOptions
import com.assesment.addressbook.R

const val DATABASE_NAME = "address_book_db5"

val OPTIONS = navOptions {
    anim {
        enter = R.anim.fade_in
        exit = R.anim.fade_out
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}