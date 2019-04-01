package com.example.lunarphases

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import java.text.Format
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class MoonPhaseCalculator{



    fun Simple(year: Int, month: Int, day:Int): Long {
        val lp = 2551443
        val now : Calendar = Calendar.getInstance()
        now.set(year,month-1,day,20,35,0)
        val new_moon: Calendar = Calendar.getInstance()
        new_moon.set(1900,0,1,15,14,0)
//        new_moon.set(1970,0,7,20,35,0)
        val phase = (now.timeInMillis - new_moon.timeInMillis)/1000 %lp
        return Math.floor((phase / (24 * 3600)).toDouble()).toLong() + 1
    }

    fun Conway(year: Int, month: Int, day: Int): Long {
        var r : Double = (year % 100).toDouble()
        r %= 19
        if (r > 9){ r -= 19}
        r = ((r * 11) % 30) + month + day;
        if (month<3){r += 2}
        if(year<2000) r-=4 else r-=8.3
        r = Math.floor(r+0.5)%30
        return if (r < 0) (r+30).toLong() else r.toLong()
    }


    fun Trig1(year: Int,month: Int,day: Int): Long {
        val thisJD = julday(year,month,day)
        val degToRad = 3.14159265 / 180
        val K0: Double = Math.floor((year-1900)*12.3685)
        val T: Double = (year-1899.5) / 100
        val T2 : Double = T*T; val T3: Double = T*T*T
        val J0: Double = 2415020 + 29*K0
        val F0: Double = 0.0001178*T2 - 0.000000155*T3 + (0.75933 + 0.53058868*K0) - (0.000837*T + 0.000335*T2)
        val M0 = 360*(GetFrac(K0*0.08084821133)) + 359.2242 - 0.0000333*T2 - 0.00000347*T3
        val M1 = 360*(GetFrac(K0*0.07171366128)) + 306.0253 + 0.0107306*T2 + 0.00001236*T3
        val B1 = 360*(GetFrac(K0*0.08519585128)) + 21.2964 - (0.0016528*T2) - (0.00000239*T3)
        var phase = 0
        var jday: Double = 0.0
        var oldJ :Double = 0.0
        while (jday < thisJD) {
            var F = F0 + 1.530588*phase
            val M5 = (M0 + phase*29.10535608)*degToRad
            val M6 = (M1 + phase*385.81691806)*degToRad
            val B6 = (B1 + phase*390.67050646)*degToRad
            F -= 0.4068*Math.sin(M6) + (0.1734 - 0.000393*T)*Math.sin(M5)
            F += 0.0161*Math.sin(2*M6) + 0.0104*Math.sin(2*B6)
            F -= 0.0074*Math.sin(M5 - M6) - 0.0051*Math.sin(M5 + M6)
            F += 0.0021*Math.sin(2*M5) + 0.0010*Math.sin(2*B6-M6)
            F += 0.5 / 1440
            oldJ = jday
            jday = J0 + 28*phase + Math.floor(F)
            phase++
        }
        return ((thisJD-oldJ)%30).toLong()
    }

    fun Trig2(year: Int,month: Int,day: Int): Long {
        val n = Math.floor(12.37 * (year -1900 + ((1.0 * month - 0.5)/12.0)))
        val RAD = 3.14159265/180.0;
        val t = n / 1236.85;
        val t2 = t * t;
        val as1 = 359.2242 + 29.105356 * n;
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2;
        var xtra: Double = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2;
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * as1) - 0.4068 * Math.sin(RAD * am);
        val i: Double
        if (xtra > 0.0) i = Math.floor(xtra) else i = Math.ceil(xtra - 1.0)
        val j1 = julday(year,month,day)
        val jd = (2415020 + 28 * n) + i
        return ((j1-jd + 30)%30).toLong()
    }



    fun GetFrac(fr :Double) : Double{
        return (fr - Math.floor(fr))
    }

    fun julday(yr: Int, month: Int, day: Int): Double {
        var year = yr
        if (year < 0) { year++ }
        var jy = year
        var jm = month +1
        if (month <= 2) {jy--;	jm += 12	}
        var jul = Math.floor(365.25 *jy) + Math.floor(30.6001 * jm) + day + 1720995
        if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
            var ja = Math.floor(0.01 * jy);
            jul = jul + 2 - ja + Math.floor(0.25 * ja);
        }
        return jul;
    }

    fun calculate(year: Int,month: Int,day: Int, algorithm: String): Long {
        when {
            algorithm.equals("Simple") -> return Simple(year,month,day)
            algorithm.equals("Conway") -> return Conway(year,month,day)
            algorithm.equals("Trig1") -> return Trig1(year, month,day)
            algorithm.equals("Trig2") -> return Trig2(year,month,day)
            else -> return -1
        }

    }

    fun givePercentForDay(year: Int,month: Int,day: Int, algorithm: String): Long {
        val phaseDay = calculate(year,month,day,algorithm)
        if(phaseDay<15)
            return ((phaseDay/15.0)*100).toLong()
        else
            return (abs(((phaseDay-15)/15.0)-1)*100).toLong()
    }

    fun lastNewMoon(year: Int,month: Int,day:Int, algorithm: String): Calendar { //w javie miesiace od 0 a nie od 1
        val date : Calendar = Calendar.getInstance()
        date.set(year,month-1,day)
        var x = calculate(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),algorithm) //pokaze jesli dzisiaj jest nów
        while(x.toInt()!=0){
            date.add(Calendar.DAY_OF_MONTH,-1)
            x=calculate(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),algorithm)
        }
        return date
    }

    fun nextFullMoon(year: Int,month: Int,day:Int, algorithm: String): Calendar {
        val date : Calendar = Calendar.getInstance()
        date.set(year,month-1,day)
        var x = calculate(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),algorithm) //pokaze jesli dzisiaj jest nów
        while(x.toInt()!=15){
            date.add(Calendar.DAY_OF_MONTH,1)
            x=calculate(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),algorithm)
        }
        return date
    }


    fun allFullMoon(year: Int, algorithm: String): ArrayList<String> {
        val date : Calendar = Calendar.getInstance()
        var listOfDate = mutableListOf<Calendar>()
        date.set(year,0,1)
        while(date.get(Calendar.YEAR)!=year+1){
            var x = calculate(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1,date.get(Calendar.DAY_OF_MONTH),algorithm)
            if(x.toInt()==15) {
                listOfDate.add(date.clone() as Calendar)
            }
            date.add(Calendar.DAY_OF_MONTH,1)
        }

        val listItems = arrayListOf<String>()
        for(a in listOfDate){
            listItems.add(calToStr(a))
        }

        return listItems
    }


    fun calToStr(cal: Calendar): String {
        if(cal.get(Calendar.MONTH)+1<10)
            return cal.get(Calendar.DAY_OF_MONTH).toString().plus(".0").plus((cal.get(Calendar.MONTH)+1).toString()).plus(".").plus(cal.get(Calendar.YEAR))
        else
            return cal.get(Calendar.DAY_OF_MONTH).toString().plus(".").plus((cal.get(Calendar.MONTH)+1).toString()).plus(".").plus(cal.get(Calendar.YEAR))
        //wyswietlanie daty
        //val format : DateFormat = DateFormat.getDateInstance("yyy MM dd")
    }

}


