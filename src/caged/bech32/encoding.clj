(ns caged.bech32.encoding
  (:import (com.exceptionfactory.jagged.bech32
            Bech32 Bech32$Decoder Bech32$Encoder Bech32Address)))

(defn get-decoder
  "Returns a Bech32 Decoder instance."
  []
  (Bech32/getDecoder))

(defn get-encoder
  "Returns a Bech32 Encoder instance."
  []
  (Bech32/getEncoder))

(defn decode
  "Decodes a Bech32 encoded string.
  - `decoder`: Bech32 Decoder instance.
  - `encoded`: Bech32 encoded string.

  Returns a map with keys :human-readable-part and :data."
  [^Bech32$Decoder decoder ^String encoded]
  (let [address (.decode decoder encoded)]
    {:human-readable-part (.getHumanReadablePart address)
     :data (.getData address)}))

(defn encode
  "Encodes a human-readable part and data into a Bech32 encoded string.
  - `encoder`: Bech32 Encoder instance.
  - `human-readable-part`: Human-readable part as a string.
  - `data`: Data as a byte array.

  Returns the Bech32 encoded string."
  [^Bech32$Encoder encoder ^String human-readable-part ^bytes data]
  (.encode encoder human-readable-part data))
