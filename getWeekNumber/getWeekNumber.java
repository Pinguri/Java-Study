package week;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class getWeekNumber {

	public static void main(String[] args) {
		
		HashMap<String, Object> paramMap = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		String startDate = sc.next();
		String endDate = sc.next();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);

		List<String> resultList = getWeekOfMonthList(paramMap);
		
	}
	
	
	
	public static List<String> getWeekOfMonthList(Map<String, Object> map) {
		List<String> weekOfMonthList = new ArrayList<>();
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
			Date startDate = sdf.parse(map.get("startDate").toString());
			Date endDate = sdf.parse(map.get("endDate").toString());

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);



			int endYear = endCalendar.get(Calendar.YEAR);
			int endMonth = endCalendar.get(Calendar.MONTH) + 1;
			int endDay = endCalendar.get(Calendar.DATE);

			String endCaledarBestWeek = getCurrentWeekOfMonth(endYear,endMonth,endDay);
			
			int i = 0;
			
			while(true) {
				
				if(i != 0) {
					startCalendar.add(Calendar.DAY_OF_MONTH, + 7);
				}
				
				int startYear = startCalendar.get(Calendar.YEAR);
				int startMonth = startCalendar.get(Calendar.MONTH) + 1;
				int startDay = startCalendar.get(Calendar.DATE);
				
				weekOfMonthList.add(getCurrentWeekOfMonth(startYear,startMonth,startDay));
				
				if(weekOfMonthList.get(i).equals(endCaledarBestWeek) || i >= 52) break;
	
				i++;
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e);
			//logger.error(e.getMessage(), e);
		}
		
		return weekOfMonthList;
	}
	
	
	  public static String getCurrentWeekOfMonth(int year, int month, int day)  {
			 
		// 입력받은 날짜데이터의 해당월의 1일을 수요일을 기준으로 나누는 함수
		// firstWeekNumber 값이 -1 이 나오는 경우에는 이전 달 정보를 가져와야한다.
		// firstWeekNumber 값이 0 인경우는 Calendar의 기본 주차 구하는 공식 적용
		// firstWeekNumber 값이 1 인경우는 Calendar의 기본 주차 구하는 공식에 1을 빼줌
		int firstWeekNumber = subWeekNumberIsFirstDayAfterWednesday(year, month, day);
		
		// lastWeekNumber 값이 0 인경우는 Calendar의 기본 주차 구하는 공식 적용
	    int lastWeekNumber = addMonthIsLastDayBeforeWednesday(year, month, day);
	    
	    // lastWeekNumber 값이 1보다 크다는건 해당 월의 마지막 주차의 요일이 월,화요일이란 것이다.
	    // 따라서 다음주로 넘긴다.
	    if (lastWeekNumber > 0) {
	    // 해당 날짜인 day 를 1로 초기화 시킨다.
	    // 해당 월은 아래쪽에 코드에서 다음달로 넘길것이다.
	      day = 1;
	      firstWeekNumber = 0;
	    }
	    
	    if (firstWeekNumber < 0)  {
	      //Calendar 를 이전 달 정보로 세팅
	      Calendar calendar = Calendar.getInstance(Locale.KOREA);
	      calendar.set(year, month - 1, day);	
	      calendar.add(Calendar.DATE, -1);
	      
	      return getCurrentWeekOfMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
	    }

	    //Calendar 초기화
	    Calendar calendar = Calendar.getInstance(Locale.KOREA);
	    calendar.setFirstDayOfWeek(Calendar.MONDAY);
	    calendar.setMinimalDaysInFirstWeek(1);
	    calendar.set(year, month - (1 - lastWeekNumber), day);
	    // lastWeekNumber 이 1이 나온경우 Calendar를 다음달로 세팅한다.
	    
	    int dayOfWeekForFirstDayOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) - firstWeekNumber;
	    
	    //dayOfWeekForFirstDayOfMonth 인경우 기존 날짜에서 1일을 뺀 날자를 getCurrentWeekOfMonth 함수로 재귀
	    if(dayOfWeekForFirstDayOfMonth == 0) {
	      calendar.set(year, month - 1, day);
	      calendar.add(Calendar.DATE, -1);
	      return getCurrentWeekOfMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE));
	    }
	    return 
	    year + "-" + 
	    (Integer.toString(calendar.get(Calendar.MONTH) + 1).length() > 1 ? Integer.toString(calendar.get(Calendar.MONTH) + 1) : "0" + Integer.toString(calendar.get(Calendar.MONTH) + 1))  + "-" + 
	    dayOfWeekForFirstDayOfMonth;
	  
	  }
	  
	  

		 public static int subWeekNumberIsFirstDayAfterWednesday(int year, int month, int day)  {
			 
		 	//Calendar 를 입력받은 날짜로 초기화하고 day = 1일로 초기화
		    Calendar calendar = Calendar.getInstance(Locale.KOREA);
		    calendar.set(year, month - 1, day);
		    calendar.set(Calendar.DAY_OF_MONTH, 1);
		    // 해당 주의 시작일을 월요일로 셋팅
		    calendar.setFirstDayOfWeek(Calendar.MONDAY);
		    
		    // 1일로 초기화한 Calendar의 요일 
		    int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);
		    
		    // 입력받은 날짜의 해당 월의 1일이 월,화,수 요일경우
		    if ((weekOfDay >= Calendar.MONDAY) && (weekOfDay <= Calendar.WEDNESDAY)) {
		    	return 0;
		    } 
		    // 입력받은 날짜가 1이고 입력받은 날짜의 해당 월의 1일이 월,화,수요일이 아닐 경우
		    else if (day == 1 && (weekOfDay < Calendar.MONDAY || weekOfDay > Calendar.WEDNESDAY)) {
		    	return -1;
		    } else {
		    	return 1;
		    }
		 }

		  public static int addMonthIsLastDayBeforeWednesday(int year, int month, int day) {
			
			//Calendar 를 파라미터로 입력받은 날짜로 초기화
		    Calendar calendar = Calendar.getInstance(Locale.KOREA);
		    calendar.setFirstDayOfWeek(Calendar.MONDAY);
		    calendar.set(year, month - 1, day);

		    // 자바 Calendar 의 해당 주의 주차 구하기 방식
		    int currentWeekNumber = calendar.get(Calendar.WEEK_OF_MONTH);
		    // 자바 Calendar 의 해당 주의 최고 주차 구하기 방식
		    int maximumWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

		    if (currentWeekNumber == maximumWeekNumber) {
		    	calendar.set(year, month - 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		    	int maximumDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		    	//월의 마지막 주차에 요일이 월,화인 경우 주차를 다음 주차정보를 가져와야함
			    if (maximumDayOfWeek < Calendar.WEDNESDAY && maximumDayOfWeek > Calendar.SUNDAY) {
			    	return 1;
			    } else {
			        return 0;
			    }
		    } else {
		      return 0;
		    }
		  }


	
	
	
	
	

}
