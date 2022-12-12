/**
 * @brief
 *
 * @file adv_msd_v5.h
 * @date 2022-08-16
 * @author tanfanliu
 * @copyright (c) 2022-2027 by Druid Technology Co., Ltd.  All Rights Reserved.
 */

#ifndef __ADV_MSD_V5_H__
#define __ADV_MSD_V5_H__
#include <stdbool.h>
#include <stdint.h>

#define OPTIONAL_DATA_MAX_LEN       7

enum {
  VOL_TYPE_POWER = 0,
  VOL_TYPE_VOLTAGE = 1,
};

#pragma pack(push,1)
typedef struct {
  struct {
    int16_t value : 15; // 精确到0.1度
    uint16_t type : 1; // 1 - 高精度
  } temperature;
  uint16_t odba;
} optional_data_1_t;

typedef struct adv_msd_v5_s {
  struct {
    uint8_t version : 5;
    uint8_t option_data_type : 3;
  } header;

  // SN
  uint8_t sn[5];

  // plat type
  uint8_t platform;

  // customer id
  uint8_t customer_id;

  // firmware version
  uint16_t fw_version;

  // hardware version
  uint16_t hw_version;

  // voltage / power
  struct {
    uint16_t value : 15;
    uint16_t type : 1;
  } voltage;

  // hash
  struct {
    uint16_t cfg : 12;
    uint16_t adv : 4;
  } hash;

  // device status
  struct {
    uint8_t time : 4;
    uint8_t data : 3;
    uint8_t active : 1;
  } dev_status;

  // optional data
  union {
    uint8_t data[OPTIONAL_DATA_MAX_LEN];
    optional_data_1_t op_data_1;
  } op_data;
} adv_msd_v5_t;
#pragma pack(pop)

bool adv_msd_v5_decode(adv_msd_v5_t* msd, uint8_t* buf, uint16_t length);
int adv_msd_v5_encode(const adv_msd_v5_t* msd, uint8_t* buf, uint8_t buf_size);

#endif // __ADV_MSD_V2_H__
