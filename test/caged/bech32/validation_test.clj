(ns caged.bech32.validation-test
  (:require
   [caged.bech32.validation :refer [validate]]
   [clojure.test :refer [are deftest testing]]))

(deftest test-validate
  (testing "Good inputs"
    (are [input] (validate input) "bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq"
         "bc11e2o92qtg8nhmq915br542ctjqatm3tazmuou9m"
         "bc1ju9nvbpimfvw5aodecc5pu4pdzmgwbe6f1d7unf"
         "bc16h7g9cwe7m6xj405gqqmjewi9sa8pdu5koefywv"
         "bc1wydg5h0m94fvc6i3vjlf08wlbvefucsntrp3zj2"
         "bc1jfgxavp0xw2zye7rglo3xbkoyvr2fahdbuj7pvm"
         "bc133t141hy4r282mfrzlkfkfo3wbno3flb8h0ypn5"
         "bc1dmk4yu6a77jfaisigredyv76zauzrjv0vm58yhm"
         "bc1itmqu3hnuz1ju0rfu5dsgoscit6cjqmiarajfvv"
         "bc1svngw7mvoce9lrt1qj1xis52ss0n5uzg5oiabo8"
         "bc1d2dswv9nb6mtd2qp2p674o8f105rm2safr68j5s")
    (testing "Bad inputs"
      (are [input] (false? (validate input))
        "h37pjgi"
        ""
        "90"
        ".,--\\y"
        ""
        "bc1d2dswv9nb6mtd2qp2p674o8f105rm2"
        "bc1d2dswv9nb6mtd2qp2p674o8f105rm2nrrrrrrrrrrrrrrrrr"
        "bc1d2dswv9nb6mtd2qp2p674o8f105rm2SAFR68j5s"))))
