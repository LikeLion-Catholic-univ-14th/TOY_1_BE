# MeetPick

모임 일정 조율을 도와주는 AI 기반 서비스 **MeetPick**
현재 데이터베이스 설계와 핵심 CRUD API 개발이 완료된 상태입니다.

## 기술 스택
- **Language**: Java 17
- **Framework**: Spring Boot 4.x
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle
- **Library**: Lombok

## DB 구조 (Entity)
1. **Meeting (모임)**: 모임의 기본 정보 저장 (UUID 기반 ID 사용)
2. **Participant (참여자)**: 모임에 속한 참여자 정보
3. **Response (응답)**: 참여자가 입력한 일정 관련 텍스트 데이터
4. **AIResult (AI 결과)**: Gemini AI가 분석한 추천 일정 및 선정 이유

## API 명세 (API Endpoints)

### 1. 모임 (Meeting)
| 기능 | 메서드 | 엔드포인트 | 비고 |
| :--- | :--- | :--- | :--- |
| 모임 생성 | `POST` | `/meetings` | title, maxParticipants 전달 |
| 단건 조회 | `GET` | `/meetings/{meetingId}` | |
| 전체 목록 조회 | `GET` | `/meetings` | |

### 2. 응답 (Response)
| 기능 | 메서드 | 엔드포인트 | 비고 |
| :--- | :--- | :--- | :--- |
| 일정 응답 제출 | `POST` | `/responses` | participantId, meetingId, rawText 전달 |
| 모임별 응답 조회 | `GET` | `/responses/{meetingId}` | |

### 3. AI 분석 결과 (AIResult)
| 기능 | 메서드 | 엔드포인트 | 비고 |
| :--- | :--- | :--- | :--- |
| 모임별 결과 조회 | `GET` | `/result/{meetingId}` | AI 분석 완료 후 결과 조회 |

## 향후 작업
1. **Gemini AI API 연동**: `Response` 테이블의 `rawText`들을 수집하여 Gemini AI에 전달하고 분석 결과를 받아오는 로직 구현.
2. **분석 결과 저장**: AI가 추천한 날짜와 이유를 `AIResult` 테이블에 저장.
3. **배포**: 환경에 맞는 `application.yml` 설정 및 서버 배포. 

---
**Note**: `application.yml`의 DB 비밀번호와 설정 정보는 배포 환경에 맞게 수정이 필요합니다.
