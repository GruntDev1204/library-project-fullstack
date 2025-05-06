import { initializeApp } from "firebase/app"
import { getStorage, FirebaseStorage } from "firebase/storage"

interface FirebaseConfig {
  apiKey: string
  authDomain: string
  databaseURL: string
  projectId: string
  storageBucket: string
  messagingSenderId: string
  appId: string
  measurementId: string
}

const firebaseConfig: FirebaseConfig = {
  apiKey: "XXXXXXXXXXXXXXXXXXXXXXXX",
  authDomain: "XXXXXXXXXXXXXXXXXX.com",
  databaseURL: "XXXXXXXXXXXXXXXXXX.com",
  projectId: "XXXXXXXXXX",
  storageBucket: "XXXXXXXXXX.appspot.com",
  messagingSenderId: "xxxxxx",
  appId: "1:xxxxxx:web:xxxxxxx",
  measurementId: "G-xxxxxxx",
}

const app = initializeApp(firebaseConfig)

const storage: FirebaseStorage = getStorage(app)

export { storage }
