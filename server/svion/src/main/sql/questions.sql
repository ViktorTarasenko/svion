--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: question; Type: TABLE; Schema: public; Owner: svion; Tablespace: 
--

CREATE TABLE question (
    id bigint NOT NULL,
    correct_answer smallint NOT NULL,
    cost character varying(255) NOT NULL,
    image text NOT NULL,
    theme character varying(255) NOT NULL,
    time_for_answer bigint NOT NULL,
    var1 text NOT NULL,
    var2 text NOT NULL,
    var3 text NOT NULL,
    var4 text NOT NULL,
    text text NOT NULL
);


ALTER TABLE public.question OWNER TO svion;

--
-- Name: question_id_seq; Type: SEQUENCE; Schema: public; Owner: svion
--

CREATE SEQUENCE question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.question_id_seq OWNER TO svion;

--
-- Name: question_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: svion
--

ALTER SEQUENCE question_id_seq OWNED BY question.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: svion
--

ALTER TABLE ONLY question ALTER COLUMN id SET DEFAULT nextval('question_id_seq'::regclass);


--
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: svion
--

COPY question (id, correct_answer, cost, image, theme, time_for_answer, var1, var2, var3, var4, text) FROM stdin;
1	1	ONE_HUNDRED	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
2	1	TWO_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
3	1	TWO_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
4	1	THREE_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
5	1	THREE_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
6	1	FOUR_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
7	1	FOUR_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
8	1	FIVE_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
9	1	SIX_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
10	1	EIGHT_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
11	1	NINE_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
12	1	ONE_THOUSAND	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
13	1	ONE_THOUSAND_TWO_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
14	1	ONE_THOUSAND_FIVE_HUNDREDS	mocas.jpg	FASHION	15000	мокасины	кроссовки	туфли	сапоги	Какой вид современной обуви на картинке?
15	3	ONE_HUNDRED	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
16	3	TWO_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
17	3	TWO_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
18	3	THREE_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
19	3	THREE_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
20	3	FOUR_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
21	3	FOUR_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
22	3	FIVE_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
23	3	SIX_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
24	3	EIGHT_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
25	3	NINE_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
26	3	ONE_THOUSAND	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
27	3	ONE_THOUSAND_TWO_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
28	3	ONE_THOUSAND_FIVE_HUNDREDS	alice.jpg	BOOKS_WORLD	15000	гарри поттер	зеленый слоник	алиса в стране чудес	приключения тома сойера	Из какой книги иллюстрация?
29	3	ONE_HUNDRED	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
30	3	TWO_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
31	3	TWO_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
32	3	TWO_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
33	3	THREE_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
34	3	THREE_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
35	3	FOUR_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
36	3	FOUR_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
37	3	FIVE_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
38	3	SIX_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
39	3	EIGHT_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
40	3	NINE_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
41	3	ONE_THOUSAND	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
42	3	ONE_THOUSAND_TWO_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
43	3	ONE_THOUSAND_FIVE_HUNDREDS	zipun.jpg	ALL_ABOUT_FASHION	15000	Греция	Рим	Древняя Русь	Сирия	В каком государстве использовалась одежда изображенная на картинке?
44	3	ONE_HUNDRED	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
45	3	TWO_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
46	3	TWO_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
47	3	TWO_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
48	3	THREE_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
49	3	THREE_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
50	3	FOUR_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
51	3	FOUR_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
52	3	FIVE_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
53	3	SIX_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
54	3	EIGHT_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
55	3	NINE_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
56	3	ONE_THOUSAND	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
57	3	ONE_THOUSAND_TWO_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
58	3	ONE_THOUSAND_FIVE_HUNDREDS	katskill.jpg	ROUND_THE_WORLD	15000	Шаста	Апаллачи	Катскилл	Гранд-каньон	На фото горы в сша, какие?
59	3	ONE_HUNDRED	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
60	3	TWO_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
61	3	TWO_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
62	3	TWO_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
63	3	THREE_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
64	3	THREE_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
65	3	FOUR_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
66	3	FOUR_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
67	3	FIVE_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
68	3	SIX_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
69	3	EIGHT_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
70	3	NINE_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
71	3	ONE_THOUSAND	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
72	3	ONE_THOUSAND_TWO_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
73	3	ONE_THOUSAND_FIVE_HUNDREDS	flower.jpg	FLOWERS	15000	Акация	Сирень	Гвоздика	Ромашка	На фото цветок, какой?
74	3	ONE_HUNDRED	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
75	3	TWO_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
76	3	TWO_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
77	3	TWO_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
78	3	THREE_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
79	3	THREE_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
80	3	FOUR_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
81	3	FOUR_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
82	3	FIVE_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
83	3	SIX_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
84	3	EIGHT_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
85	3	NINE_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
86	3	ONE_THOUSAND	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
87	3	ONE_THOUSAND_TWO_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
88	3	ONE_THOUSAND_FIVE_HUNDREDS	bigben.jpg	PAROLE_OF_HONOR	15000	Спасская	Останкинская	Бигбен	Тауэр	Как называется башня на фото?
\.


--
-- Name: question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: svion
--

SELECT pg_catalog.setval('question_id_seq', 88, true);


--
-- Name: question_pkey; Type: CONSTRAINT; Schema: public; Owner: svion; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id);


--
-- Name: cost; Type: INDEX; Schema: public; Owner: svion; Tablespace: 
--

CREATE INDEX cost ON question USING btree (cost);


--
-- Name: theme; Type: INDEX; Schema: public; Owner: svion; Tablespace: 
--

CREATE INDEX theme ON question USING btree (theme);


--
-- PostgreSQL database dump complete
--

