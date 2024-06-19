(ns caged.bech32.encoding-test
  (:require [clojure.test :refer [deftest is testing]]
            [caged.bech32.encoding :as encoding]))

(deftest test-encoder
  (testing "Bech32 encoding"
    (let [encoder (encoding/get-encoder)
          human-readable-part "bc"
          data (byte-array [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])
          encoded (encoding/encode encoder human-readable-part data)]
      (is (= encoded "bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kv8f3t4"))
      (is (= (encoding/decode (encoding/get-decoder) encoded)
             {:human-readable-part human-readable-part
              :data data}))))

  (testing "Bech32 encoding with invalid input"
    (let [encoder (encoding/get-encoder)
          human-readable-part "bc"
          data (byte-array [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])]
      (is (thrown? IllegalArgumentException
                   (encoding/encode encoder human-readable-part data))))))

(deftest test-decoder
  (testing "Bech32 decoding"
    (let [decoder (encoding/get-decoder)
          human-readable-part "bc"
          data (byte-array [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])
          encoded (encoding/encode (encoding/get-encoder) human-readable-part data)]
      (is (= (encoding/decode decoder encoded)
             {:human-readable-part human-readable-part
              :data data})))))
