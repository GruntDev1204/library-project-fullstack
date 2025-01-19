import { getStorage, FirebaseStorage } from "firebase/storage"
import { initializeApp } from "firebase/app"

interface FirebaseConfig {
  apiKey: string
  authDomain: string
  databaseURL?: string
  projectId: string
  storageBucket: string
  messagingSenderId: string
  appId: string
  measurementId: string
}

const firebaseConfig: FirebaseConfig = {
  apiKey: "xxx-xxxxxxxx",
  authDomain: "xxx",
  projectId: "xxx",
  storageBucket: "xxx.appspot.com",
  messagingSenderId: "xxx",
  appId: "xxx",
  measurementId: "xxx",
}

const app = initializeApp(firebaseConfig)

const storage: FirebaseStorage = getStorage(app)

export { storage }
