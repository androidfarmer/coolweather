package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {
	/**
	 * 解析服务器返回的省级数据
	 */
	public synchronized static boolean handleProvinceResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					Province province = new Province();
					String[] array = p.split("\\|");
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}

		}
		return false;

	}

	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public synchronized static boolean handleCityResponse(
			CoolWeatherDB coolWeatherDB, String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String s : allCities) {
					String[] array = s.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					coolWeatherDB.saveCity(city);
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * 解析服务器返回的县级数据
	 */
	public synchronized static boolean handleCountyResponse(
			CoolWeatherDB coolWeatherDB, String response, int cityId) {
		String[] allCounties = response.split(",");
		if (allCounties != null && allCounties.length > 0) {
			for (String s : allCounties) {
				County county = new County();
				String[] array = s.split("\\|");
				county.setCityId(cityId);
				county.setCountyCode(array[0]);
				county.setCountyName(array[1]);
				coolWeatherDB.saveCounty(county);
			}
			return true;
		}
		return false;
	}

}
