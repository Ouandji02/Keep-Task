package com.phone.keeptask.domain.model

sealed class Navigation(val route : String){
    object Home : Navigation(route = "home")
    object Detail : Navigation(route = "detail")
    object Create : Navigation(route = "create")
    object Update : Navigation(route = "update")
}
