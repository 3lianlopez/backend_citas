//package com.software.citas.auth.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//
//@Entity
//@Table(
//        name = "otp_codes",
//        indexes = {
//                @Index(
//                        name = "idx_otp_user_usado_creado",
//                        columnList = "user_id, usado, creado_en"
//                ),
//                @Index(
//                        name = "idx_otp_expira_en",
//                        columnList = "expira_en"
//                )
//        }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class OtpCode {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "user_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_otp_codes_usuario")
//    )
//    private Usuario usuario;
//
//    @Column(name = "codigo_hash", nullable = false, length = 255)
//    private String codigoHash;
//
//    @Column(name = "expira_en", nullable = false)
//    private LocalDateTime expiraEn;
//
//    @Builder.Default
//    @Column(nullable = false)
//    private Integer intentos = 0;
//
//    @Builder.Default
//    @Column(nullable = false)
//    private boolean usado = false;
//
//    @Column(name = "creado_en", nullable = false, updatable = false)
//    private LocalDateTime creadoEn;
//
//    @PrePersist
//    public void prePersist() {
//        if (creadoEn == null) {
//            creadoEn = LocalDateTime.now();
//        }
//    }
//}