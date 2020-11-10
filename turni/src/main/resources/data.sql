INSERT INTO TURNI VALUES(1,'11/11/2020',1);
INSERT INTO TURNI VALUES(1,'11/11/2020',1);
INSERT INTO TURNI VALUES(1,'11/11/2020',1);
INSERT INTO TURNI VALUES(1,'11/11/2020',1);







@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "Data")
    private Date date;

    @Column(name = "id_dip")
    private Integer id_dip;