package com.soen390.conumap.helper

import android.content.Context
import com.soen390.conumap.map.Map
import com.soen390.conumap.permission.Permission

object ContextPasser {
    private lateinit var context: Context

    // Context passed to ContextPasser and all other files that require context.
    fun setContexts(ctx: Context) {
        context = ctx

        Map.setContext(ctx)
        Permission.setContext(ctx)
        DeviceLocationChecker.setUp(ctx)
    }
}
