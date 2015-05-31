(ns cheque)

(def humanized-numbers
  {1 "um",
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

(declare humanize)

(defn humanize-dezena [money]
  (let [mod-10 (mod money 10)]
    (cond
      (or (<= money 20) (zero? mod-10)) (humanized-numbers money)
      (> money 20) (str (humanize-dezena (- money mod-10))
                        " e "
                        (humanize-dezena mod-10)))))

(defn humanize-centena [money]
  (cond
    (= money 100) "cem"
    (zero? (mod money 100)) (humanized-numbers money)
    :else (str (humanize-centena (- money (mod money 100)))
               " e "
               (humanize-dezena (mod money 100)))))

(defn humanize-milhar [money]
  (str
    (humanize (quot money 1000)) " mil"
    (if (> (mod money 1000) 0)
      (str " e " (humanize (mod money 1000))))))

(defn humanize [money]
  (let [len (count (str money))]
    (cond
      (<= len 2) (humanize-dezena money)
      (= len 3) (humanize-centena money)
      (>= len 4) (humanize-milhar money))))

(defn humanize-with-currency [money]
  (let [reais (Math/abs (int money))
        cents (int (* 100
                      (- (BigDecimal. (str money)) reais)))
        sinal (if (neg? money)
                "menos ")
        humReais (cond
                   (= reais 1) (str (humanize reais) " real")
                   :else (str (humanize reais) " reais"))
        humCents (cond
                   (= cents 1) (str (humanize cents) " centavo")
                   :else (str (humanize cents) " centavos"))]
  (str
    sinal
    (cond
      (and (> cents 0) (> reais 0)) (str humReais " e " humCents)
      (> reais 0) humReais
      (> cents 0) humCents))))

