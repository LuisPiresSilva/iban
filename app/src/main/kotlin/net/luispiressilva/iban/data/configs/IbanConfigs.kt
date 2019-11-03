package net.luispiressilva.iban.data.configs

import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by Luis Silva on 01/11/2019.
 */
class IbanConfigs {

    companion object {

        //DATES
        //INPUT
        val formatter_yyyy_MM_dd_T_HH_mm_ss_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        //OUTPUT
        val formatter_yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    }
}