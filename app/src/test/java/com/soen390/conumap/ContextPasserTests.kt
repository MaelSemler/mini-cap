package com.soen390.conumap

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.maps.model.MarkerOptions
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.soen390.conumap.helper.ContextPasser
import org.junit.Test
import org.mockito.internal.matchers.Any

class ContextPasserTests {

    var locationManagerMock: LocationManager = mock()

    var context: Context = mock {
        on {getSystemService(Context.LOCATION_SERVICE)}.doReturn(locationManagerMock)
    }

    //Check if the ContextPasser passes the context correctly
    @Test
    fun setContextTest() {
        ContextPasser.setContexts(context)
        assert(context.getSystemService(Context.LOCATION_SERVICE) == locationManagerMock)
    }
}