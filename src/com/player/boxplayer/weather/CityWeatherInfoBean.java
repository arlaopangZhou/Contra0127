package com.player.boxplayer.weather;

import java.io.Serializable;

public class CityWeatherInfoBean
  implements Serializable
{
  private static final long serialVersionUID = -4553664905800791973L;
  String cityId;
  String cityName = "";
  String dnstr = "";
  String fTemp;
  String tTemp;
  int imag1;
  int imag2;
  
  String weatherInfo;

  public String getCityId()
  {
    return this.cityId;
  }

  public String getCityName()
  {
    return this.cityName;
  }

  public String getDnstr()
  {
    return this.dnstr;
  }

  public int getImag1()
  {
    return this.imag1;
  }

  public int getImag2()
  {
    return this.imag2;
  }

  public String getWeatherInfo()
  {
    return this.weatherInfo;
  }

  public String getfTemp()
  {
    return this.fTemp;
  }

  public String gettTemp()
  {
    return this.tTemp;
  }

  public void setCityId(String paramString)
  {
    this.cityId = paramString;
  }

  public void setCityName(String paramString)
  {
    this.cityName = paramString;
  }

  public void setDnstr(String paramString)
  {
    this.dnstr = paramString;
  }

  public void setImag1(int paramInt)
  {
    this.imag1 = paramInt;
  }

  public void setImag2(int paramInt)
  {
    this.imag2 = paramInt;
  }

  public void setWeatherInfo(String paramString)
  {
    this.weatherInfo = paramString;
  }

  public void setfTemp(String paramString)
  {
    this.fTemp = paramString;
  }

  public void settTemp(String paramString)
  {
    this.tTemp = paramString;
  }

  public String toString()
  {
    return "CityWeatherInfoBean [cityName=" + this.cityName + ", cityId=" + this.cityId + ", fTemp=" + this.fTemp + ", tTemp=" + this.tTemp + ", weatherInfo=" + this.weatherInfo + ", dnstr=" + this.dnstr + ", imag1=" + this.imag1 + ", imag2=" + this.imag2 + "]";
  }
}