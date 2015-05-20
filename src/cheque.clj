(ns cheque)

(def humanizedNumbers {
  1 "um",
  2 "dois",
  3 "tres",
  4 "quatro",
  5 "cinco",
  6 "seis",
  7 "sete",
  8 "oito",
  9 "nove",
  10 "dez",
  11 "onze",
  12 "doze",
  13 "treze",
  14 "quatorze",
  15 "quinze",
  16 "dezesseis",
  17 "dezessete",
  18 "dezoito",
  19 "dezenove",
  20 "vinte",
  30 "trinta",
  40 "quarenta",
  50 "cinquenta",
  60 "sessenta",
  70 "setenta",
  80 "oitenta",
  90 "noventa",
  100 "cento",
  200 "duzentos",
  300 "trezentos",
  400 "quatrocentos",
  500 "quinhentos",
  600 "seicentos",
  700 "setecentos",
  800 "oitocentos",
  900 "novecentos"})

(defn humanize [money])

(defn humanizeDezena [money]

  (cond
    (or (<= money 20) (= (mod money 10) 0)) (get humanizedNumbers money)
    (> money 20) (str (humanizeDezena (- money (mod money 10))) " e " (humanizeDezena (mod money 10)))
    :else ""))

(defn humanizeCentena [money]
  (cond
    (= money 100) "cem"
    (= (mod money 100) 0) (get humanizedNumbers money)
    :else (str (humanizeCentena (- money (mod money 100))) " e " (humanizeDezena (mod money 100)))))

(defn humanizeMilhar [money]
  (str
    (humanize (quot money 1000)) " mil"
    (cond
      (> (mod money 1000) 0) (str " e " (humanize (mod money 1000)))
      :else "")))

(defn humanize [money]

  (def len (alength (to-array (str money))))
  (cond
    (<= len 2) (humanizeDezena money)
    (= len 3) (humanizeCentena money)
    (>= len 4) (humanizeMilhar money)
    :else "" ))

(defn humanizeWithReais [money]
  (str (humanize money) " reais")
  )

(defn humanizeWithCurrency [money]
  (def reais (int money))
  (def cents (int (* 100 (float (- money reais)))))

  (cond
    (> cents 0) (str (humanizeWithReais reais) " e " (humanize cents) " centavos")
    :else (humanizeWithReais money)
    )

  )

