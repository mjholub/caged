(ns caged.bech32.encoding-test
  (:require [clojure.test :refer [deftest is testing]]
            [caged.bech32.encoding :as encoding]))

(defn- heap-char-buffer-content [buffer]
  (let [chars (char-array (.remaining buffer))]
    (doto (.duplicate buffer)
      (.get chars))
    (String. chars)))

(deftest test-encoder
  (testing "Bech32 encoding"
    (let [encoder (encoding/get-encoder)
          human-readable-part "bc"
          data (byte-array (vec (range 0 15)))
          encoded (encoding/encode encoder human-readable-part data)]
      (is (= (heap-char-buffer-content encoded) "bc1qqqsyqcyq5rqwzqfpg9scrgwd8c2p3"))
      (is (= (encoding/decode (encoding/get-decoder) encoded)
             {:human-readable-part human-readable-part
              :data (vec data)}))))

  (testing "Bech32 encoding with invalid input"
    (let [encoder (encoding/get-encoder)
          human-readable-part "bc"
          data {:blah :bleh}]
      (is (thrown? ClassCastException
                   (encoding/encode encoder human-readable-part data))))))

(deftest test-decoder
  (testing "Bech32 decoding"
    (let [decoder (encoding/get-decoder)
          human-readable-part "bc"
          data (byte-array [0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15])
          encoded (encoding/encode (encoding/get-encoder) human-readable-part data)]
      (is (= (encoding/decode decoder encoded)
             {:human-readable-part human-readable-part
              :data (vec data)})))))
