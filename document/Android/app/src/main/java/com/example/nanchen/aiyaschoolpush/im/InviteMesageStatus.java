package com.example.nanchen.aiyaschoolpush.im;

/**
 * @author nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.im
 * @date 2016/10/28  09:05
 */

public enum InviteMesageStatus {
    //==contact
    /**being invited*/
    BEINVITEED,
    /**being refused*/
    BEREFUSED,
    /**remote user already agreed*/
    BEAGREED,

    //==group application
    /**remote user apply to join*/
    BEAPPLYED,
    /**you have agreed to join*/
    AGREED,
    /**you refused the join request*/
    REFUSED,

    //==group invitation
    /**received remote user's invitation**/
    GROUPINVITATION,
    /**remote user accept your invitation**/
    GROUPINVITATION_ACCEPTED,
    /**remote user declined your invitation**/
    GROUPINVITATION_DECLINED
}
