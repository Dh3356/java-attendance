# java-attendance

출석 미션 저장소

# 기능 요구 사항

- ## 출석 관리 규칙 및 시스템 설계 정책
=======
# 구현 기능 목록

## 출석 확인

- [x] 닉네임과 등교 시간을 입력하면 출석할 수 있다.
- [x] 출석 후 출석 기록을 확인할 수 있다.
- [x] 이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다.

## 출석 수정

- [x] 출석 확인을 수정하려면 닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.
    - 수정 이전의 출석 기록은 삭제되어야 한다. (추후 구현)
- [x] 수정 후에는 변경 전과 변경 후의 출석 기록을 확인할 수 있다.

## 크루별 출석 기록 확인

- [x] 닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.

## 제적 위험자 확인

- [x] 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
- [x] 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며, 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.
    - 이 기능을 구현하기 위해서 지각 3회가 결석 1회라는 비즈니스 로직을 도메인만 담당하도록 구현한다.

## 크루 닉네임

- [x] 크루의 닉네임은 2 ~ 4글자여야 한다.
    - 만약 크루 닉네임이 2 ~ 4글자가 아니면 IllegalArgumentException 을 발생시킨 후, 다시 입력받는다.

- 프로그램은 사용자가 종료할 때까지 종료되지 않으며, 해당 기능을 수행한 후 초기 화면으로 돌아간다.

- ## 출석 확인
- 닉네임과 등교 시간을 입력하면 출석할 수 있다.
- 출석 후 출석 기록을 확인할 수 있다.
- 이미 출석한 경우, 다시 출석할 수 없으며 수정 기능을 이용하도록 안내한다.

- ## 출석 수정
- 출석 확인을 수정하려면 닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.
- 수정 후에는 변경 전과 변경 후의 출석 기록을 확인할 수 있다.


- ## 크루별 출석 기록 확인
- 닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.

- ## 제적 위험자 확인
- 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
- 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력하며, 대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.

- ## 입출력 요구 사항
- ### 입력
- 자세한 입력 예시는 실행 결과 예시를 참고하며, 프로그램을 시작하면 src/main/resources/attendances.csv를 통해 구현에 필요한 정보를 조회한다.
    - 닉네임과 출석 일시가 기록되어 있다.

# Model 구현 클래스 목록

### Crew (크루 Class)

- **상태**
    - String 닉네임
- **생성**
    - 닉네임을 통해 생성된다.
        - 크루의 닉네임이 2 ~ 5글자가 아니면 `IllegalArgumentException` 을 발생시킨다.
- **동작** (getter, equals, hashCode 제외)
    - 없음

### CampusOperationPolicy (캠퍼스 운영 정책 Class)

- **상태**
    - 운영 시간 (08:00~23:00)
- **생성**
    - 기본 생성자를 사용한다.
- **동작** (getter, equals, hashCode 제외)
    - LocalDateTime 을 통해 운영 중인 중인지 판별한다.

### AttendanceStatus (출석 상태 Enum)

- **상태**
    - 이름 (`"출석"`, `"지각"`, `"결석"`)
    - 요일 별 출석, 지각, 결석 처리 시간
- **생성**
    - LocalDateTime 을 통해 시간을 받아 적절한 출석을 생성한다.
        - 만약, LocalDateTime 이 캠퍼스 운영 시간이 아니면 `IllegalArgumentException` 을 발생시킨다.
        - 5분까지는 출석이다.
        - 6분 ~ 30분은 지각이다.
        - 30분 후는 결석이다.

### CrewStatus (크루 상태 Class)

- **상태**
    - 이름 (`"경고"`, `"면담"`, `"제적"`, `"정상"`)
- **생성**
    - List<AttendanceStatus> 를 통해 생성된다.
        - 경고 대상자: 결석 2회 이상
        - 면담 대상자: 결석 3회 이상
        - 제적 대상자: 결석 5회 초과
        - 정상: 이외
- **동작**
    - 없음.

### 공휴일 (Enum)

- **상태**
    - 공휴일 목록
    -
- **동작**
    - 특정 LocalDate 가 공휴일인지 반환한다.

### AttendanceLog (출석 기록 Class)

- **상테**
    - LocalDate 출석 일.
    - LocalTime 출석 시각.
    - AttendanceStatus 출석 상태.
- **생성**
    - LocalDateTime, CampusOperationPolicy 을 받아 생성된다.
    - LocalDate 를 받아 결석 객체가 생성된다.
- **동작**
    - 없음.

### AttendanceLogs (출석 기록 일급 컬렉션 Class)

- **상테**
    - List<AttendanceLog>
- **상태**
    - 기본 생성자를 사용한다.
- **동작**
    - LocalDate from 부터 LocalDate to 내에 등교일인데 출석하지 않은 날에 대한 AttendanceLog 들과 함께 AttendanceLog 리스트를 반환한다.
    - 크루 출석 데이터를 종합하여 Map<AttendanceStatus, Integer> 를 반환한다.
        - Map<AttendanceStatus, Integer> 에는 각 AttendanceStatus 가 몇 개 있는지 저장한다.
    - AttendanceLog 를 추가한다.(크루 출석 기록을 추가한다)
    - 총 결석 횟수를 반환한다.
    - 총 지각 횟수를 반환한다.
    - 정책이 적용된 총 결석 횟수를 반환한다.
    - 정책이 적용된 총 지각 횟수를 반환한다.
    - AttendanceLog 하나를 수정한다.

### AttendanceDate (출석 일자 Class)

- **상태**
    - LocalDate 출석 일자
- **생성**
    - LocalDate 와 CampusOperationPolicy 를 받아 생성된다.
- **동작**
    - 생성 시 CampusOperationPolicy 를 통해 캠퍼스 운영 날짜인지 검증한다.
    - LocalDate 를 받아 해당 LocalDate 와 자신이 같은 날인지 반환한다.
    - DayOfWeek 를 받아 해당 요일이 자신의 요일인지 반환한다.

### AttendanceTime (출석 시간 Class)

- **상태**
    - LocalTime 출석 시간
- **생성**
    - LocalTime 과 CampusOperationPolicy 를 받아 생성된다.
    - value 가 null 인 자신을 생성한다.
- **동작**
    - 생성 시 CampusOperationPolicy 를 통해 캠퍼스 운영 시간인지 검증한다.
    - LocalTime 을 받아 해당 LocalTime 이 자신의 value 보다 이전인지 반환한다.
    - 자신의 value 가 비어 있는 null 한 시간인지 반환한다.
    - value 가 null 일수 있으므로, getter 는 Optional 로 반환한다.

### AttendanceDateTime (출석 일시 Class)

- **상태**
    - AttendanceDate 출석 일자
    - AttendanceTime 출석 시간
- **생성**
    - LocalDateTime 과 CampusOperationPolicy 를 받아 생성된다.
    - LocalDate 와 과 CampusOperationPolicy 만을 받아 시간이 null 인 자신을 생성한다.
- **동작**
    - LocalDate 를 받아 해당 LocalDate 가 자신의 출석 일자인지 반환한다.
    - LocalTime 을 받아 해당 LocalTime 이 자신의 출석 시간보다 이전인지 반환한다.
    - 자신의 출석 시간이 value 가 비어 있는 null 한 시간인지 반환한다.
    - DayOfWeek 를 받아 해당 요일이 자신의 요일인지 반환한다.

### CrewAttendanceLogDeserializer (크루 출석 기록 역직렬화기 Class)

- **상태**
    - 없음.
- **생성**
    - 기본 생성자를 사용한다.
- **동작**
    - Path 를 받아 해당 Path 의 파일에 있는 데이터를 읽어 크루 출석 기록 Map 을 반환한다.

### CrewAttendanceRepository (크루 출석 기록 저장소 Class)

- **상태**
    - Map<Crew, AttendanceLogs> 크루 출석 기록
- **생성**
    - 크루 출석 기록 역직렬화기와 크루 출석 데이터 Path 를 받아 생성된다.
- **동작** (getter, equals, hashCode 제외)
    - 크루와 AttendanceLog 를 통해 크루 출석을 추가한다.
    - 크루와 AttendanceLog 를 통해 크루 출석을 수정한다.
    - 시작 LocalDate 부터 종료 LocalDate 까지 특정 크루의 CrewAttendanceLog 를 반환한다.
=======
- 프로그램은 사용자가 종료할 때까지 종료되지 않으며, 해당 기능을 수행한 후 초기 화면으로 돌아간다.
