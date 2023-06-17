package com.wakeUpTogetUp.togetUp.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakeUpTogetUp.togetUp.alarm.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.routine.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.restdocs.snippet.Attributes.key;

@WebMvcTest(AlarmController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
class AlarmControllerTest {
//    ConstraintDescriptions simpleRequestConstraints = new ConstraintDescriptions(.class);
//    List<String> nameDescription = simpleRequestConstraints.descriptionsForProperty("name");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AlarmService alarmService;
    @MockBean
    private AlarmProvider alarmProvider;
    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("togetup.shop")
                        .withPort(80)
                )
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("getAlarm - [Get] /alarm/{alarmId}")
    void getAlarm() throws Exception{
        //given
        AlarmRes response = AlarmRes.builder()
                .id(42)
                .userId(9)
                .missionId(1)
                .name("기상알람")
                .icon("⏰")
                .sound("default")
                .isVibrate(true)
                .isRoutineOn(true)
                .snoozeInterval(5)
                .snoozeCnt(3)
                .alarmTime("06:30:00")
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .friday(true)
                .saturday(true)
                .sunday(false)
                .isActivated(true)
                .routineResList(
                        Arrays.asList(
                                new RoutineRes(5,9,"기상",10,"⏰","#000000",1),
                                new RoutineRes(7,9,"샤워",15,"🚿","#000000",2)
                        )
                )
                .build();

        given(alarmProvider.getAlarm(42)).willReturn(response);

        Integer alarmId = 42;
        //when
        ResultActions action = mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/app/alarm/{alarmId}", alarmId)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(oauth2Login())
                )
                .andDo(print());

        //then
        BaseResponse<AlarmRes> responseData = new BaseResponse<>(Status.SUCCESS, response);

        action.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseData)))
                .andExpect(jsonPath("$.result.userId").value(9))
                .andDo(
                        document("alarm-getAlarm",
                                preprocessRequest(removeHeaders("Foo")),
                                preprocessResponse(prettyPrint()),

//                                requestHeaders(
//                                        headerWithName("Authorization").description("Basic auth credentials")),
                                pathParameters(
                                        parameterWithName("alarmId").description("알람 Id")
                                                .attributes(key("type").value("int"))
                                                .attributes(key("optional").value("false"))
                                ),
                                requestParameters(
                                ),
//                                requestFields(
//
//                                ),
//                                responseHeaders(
//                                        headerWithName("X-RateLimit-Limit")
//                                                .description("The total number of requests permitted per period"),
//                                        headerWithName("X-RateLimit-Remaining")
//                                                .description("Remaining requests permitted in current period"),
//                                        headerWithName("X-RateLimit-Reset")
//                                                .description("Time at which the rate limit period will reset")
//                                ),
                                responseFields(
                                        fieldWithPath("httpStatusCode").description("http 상태코드"),
                                        fieldWithPath("httpReasonPhrase").description("http 상태코드 설명문구"),
                                        fieldWithPath("message").description("설명 메시지"),
                                        fieldWithPath("result").description("결과"),
                                        fieldWithPath("result.id").description("알람 Id"),
                                        fieldWithPath("result.userId").description("사용자 Id"),
                                        fieldWithPath("result.missionId").description("미션 Id"),
                                        fieldWithPath("result.name").description("알람 이름"),
                                        fieldWithPath("result.icon").description("아이콘"),
                                        fieldWithPath("result.sound").description("알람 벨소리"),
                                        fieldWithPath("result.isVibrate").description("진동 여부"),
                                        fieldWithPath("result.isRoutineOn").description("루틴 활성 여부"),
                                        fieldWithPath("result.snoozeInterval").description("다시울림 간격(분)"),
                                        fieldWithPath("result.snoozeCnt").description("다시울림 횟수"),
                                        fieldWithPath("result.alarmTime").description("알람 시간"),
                                        fieldWithPath("result.monday").description("월요일 알림 여부"),
                                        fieldWithPath("result.tuesday").description("화요일 알림 여부"),
                                        fieldWithPath("result.wednesday").description("수요일 알림 여부"),
                                        fieldWithPath("result.thursday").description("목요일 알림 여부"),
                                        fieldWithPath("result.friday").description("금요일 알림 여부"),
                                        fieldWithPath("result.saturday").description("토요일 알림 여부"),
                                        fieldWithPath("result.sunday").description("일요일 알림 여부"),
                                        fieldWithPath("result.isActivated").description("알람 활성 여부"),
                                        subsectionWithPath("result.routineResList[]").description("루틴 리스트")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].id").description("루틴 Id")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].alarmId").description("알람 Id")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].name").description("루틴 이름")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].estimatedTime").description("예상 소요 시간")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].icon").description("아이콘")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].color").description("색상")
                                                .attributes(key("optional").value("true")),
                                        fieldWithPath("result.routineResList[].routineOrder").description("루틴 순서")
                                                .attributes(key("optional").value("true"))
                                )
                        )
                );
    }
    @Test
    void getAlarmsByUserId() {
    }

    @Test
    void createAlarm() {
    }

    @Test
    void updateAlarm() {
    }

    @Test
    void deleteAlarm() {
    }
}