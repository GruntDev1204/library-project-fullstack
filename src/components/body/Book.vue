<template>
  <h3 class="text-center mt-5 mb-5 large" :class="classTable">
    Quản lý sách
    <i
      class="fas fa-book ml-2"
      v-on:click="isCreate = !isCreate"
      title="Create a book"
    >
    </i>

    <i
      class="fa-solid ml-3 fa-rocket effect-click"
      v-on:click="activeEffect()"
    ></i>
  </h3>

  <div class="alert alert-form" v-if="isCreate" :class="classForm">
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Tên sách</label>
      <input
        type="text"
        class="form-control"
        placeholder="Điền vào tên sách"
        v-model="insertBook.name"
      />
    </div>
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Chọn danh mục</label>
      <select class="form-control" v-model="insertBook.categoryId">
        <option v-bind:value="0">Chưa có danh mục</option>
        <option v-for="(v, k) in dataCategory" :value="v.id" :key="k">
          {{ v.name }}
        </option>
      </select>
    </div>
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Số lượng </label>
      <input
        type="number"
        class="form-control"
        placeholder="Điền vào số lượng"
        v-model="insertBook.quantity"
      />
    </div>

    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Gía</label>
      <input
        type="number"
        class="form-control"
        placeholder="Điền vào giá"
        v-model="insertBook.price"
      />
    </div>

    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Khuyến mãi</label>
      <input
        type="number"
        class="form-control"
        placeholder="Điền vào khuyễn mãi"
        v-model="insertBook.promotionValue"
      />
    </div>

    <div class="form-group flex mt-3">
      <label class="mt-3">Avatar : </label>
      <button class="btn btn-info mt-3" v-on:click="handleUploading()">
        <i class="fa-solid fa-circle-plus"></i> Upload
      </button>
    </div>

    <div
      class="alert alert-black alert-preview"
      v-if="insertBook.avatar && insertBook.avatar.length"
    >
      <img v-bind:src="insertBook.avatar" alt="preview" class="preview" />
    </div>
    <div class="row item-right mt-5">
      <button class="btn btn-success mb-3" v-on:click="createBook()">
        <i class="fa-solid fa-floppy-disk"></i> Create
      </button>
      <button class="btn btn-primary ml-3 mb-3" v-on:click="clearInput()">
        <i class="fa-solid fa-rotate"></i> Clear
      </button>
      <button class="btn btn-warning ml-3 mb-3" v-on:click="isCreate = false">
        <i class="fa-regular fa-circle-xmark"></i> Close
      </button>
    </div>
  </div>

  <div class="row mt-5" :class="classForm">
    <div class="col">
      <div class="alert alert-tabbar-2">
        <div class="left-tabbar">
          <input
            type="text"
            class="form-control"
            v-model="findBook.name"
            placeholder="tìm kiếm theo tên sách"
          />
        </div>

        <div class="center-tabbar">
          <select class="form-control" v-model="findBook.amount">
            <option value="" selected>Tìm kiếm theo số lượng</option>
            <option value="under_20">dưới 20 quyển</option>
            <option value="20_to_50">20 đến 50 quyển</option>
            <option value="50_to_100">50 đến 100 quyển</option>
            <option value="above_100">trên 100 quyển</option>
          </select>
        </div>

        <div class="right-tabbar">
          <select class="form-control" v-model="findBook.categoryId">
            <option value="0" selected>Tìm kiếm theo danh mục</option>
            <option v-for="(v, k) in dataCategory" :value="v.id" :key="k">
              {{ v.name }}
            </option>
          </select>
          <button class="btn btn-info" v-on:click="readBook()">
            <i class="fa-solid fa-magnifying-glass"></i>
          </button>
          <button class="btn btn-warning" v-on:click="clearInput()">
            <i class="fa-solid fa-rotate"></i>
          </button>
        </div>
      </div>
    </div>
  </div>

  <div class="row mt-2" :class="classForm">
    <div class="col">
      <div class="alert alert-tabbar">
        <div class="left-tabbar">
          <button class="btn btn-info" v-on:click="pageOption.index--">
            <i class="fa-solid fa-arrow-left-long"></i>
          </button>
          <input
            type="number"
            class="small-input"
            readonly
            v-model="pageOption.index"
          />
          <button class="btn btn-info" v-on:click="pageOption.index++">
            <i class="fa-solid fa-arrow-right-long"></i>
          </button>
        </div>

        <div class="center-tabbar">
          <select class="form-control" v-model="pageOption.size">
            <option v-bind:value="5">Choose Size (default 5)</option>
            <option v-bind:value="10">10</option>
          </select>
        </div>

        <div class="center-tabbar">
          <select class="form-control" v-model="pageOption.sortByCol">
            <option value="id">Sort by column (default id)</option>
            <option value="name">name</option>
            <option value="quantity">quantity</option>
            <option value="transactionVolume">Lượng giao dịch</option>
          </select>
        </div>

        <div class="right-tabbar">
          <select class="form-control" v-model="pageOption.sortOption">
            <option value="desc">Sort option (default desc)</option>
            <option value="asc">asc</option>
          </select>
        </div>
      </div>
    </div>
  </div>

  <table class="table table-striped table-dark mt-2 mb-5" :class="classTable">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Hình ảnh</th>
        <th scope="col">Tên Sách</th>
        <th scope="col">Số Lượng</th>
        <th scope="col">Gía bán</th>
        <th scope="col">Khuyến mãi</th>
        <th scope="col">Lượt giao dịch</th>
        <th scope="col">Danh mục</th>
        <th scope="col">Action</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-if="dataBook && dataBook.length"
        v-for="(v, k) in dataBook"
        :key="k"
      >
        <th scope="row">{{ k + 1 }}</th>
        <td>
          <img v-bind:src="v.avatar" alt="avatar" class="avatar-circle" />
        </td>
        <td>{{ v.name }}</td>
        <td>{{ v.quantity }}</td>
        <td>{{ formatMoney(v.price) }}</td>
        <td>{{ v.promotionValue }}%</td>
        <td>{{ v.transactionVolume }}</td>

        <td>
          {{ v.categoryName ? v.categoryName : "Loading..." }}
        </td>
        <td>
          <button class="btn btn-success mr-2" v-on:click="selectBook(v.id)">
            <i class="fas fa-edit"></i>
          </button>
          <button class="btn btn-info mr-2" v-on:click="selectAvatar(v.id)">
            <i class="fas fa-image"></i>
          </button>
          <button
            class="btn btn-danger mt-2"
            v-on:click="handleDeleteBook(v.id)"
          >
            <i class="fas fa-trash-alt"></i>
          </button>
        </td>
      </tr>
      <tr v-else>
        <td colspan="9" class="text-center">
          <i class="fa-solid fa-spinner"></i> No Data Available
        </td>
      </tr>
    </tbody>
  </table>

  <div class="modal">
    <div class="modal-content" v-if="isOpenModal">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Update sách</h5>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label class="mb-3 mt-3">Tên sách</label>
          <input
            type="text"
            class="form-control"
            placeholder="Điền vào tên sách"
            v-model="newBook.name"
          />
        </div>

        <div class="form-group mt-3">
          <label class="mb-3 mt-3">Khuyến mãi</label>
          <input
            type="number"
            class="form-control"
            placeholder="Điền vào tỉ lệ khuyến mãi"
            v-model="newBook.promotionValue"
          />
        </div>

        <div class="form-group mt-3">
          <label class="mb-3 mt-3">Chọn danh mục</label>

          <select class="form-control" v-model="newBook.categoryId">
            <option v-bind:value="0">Chưa có danh mục</option>
            <option
              v-if="dataCategory && dataCategory.length"
              :key="k"
              v-for="(v, k) in dataCategory"
              :value="v.id"
            >
              {{ v.name }}
            </option>
          </select>
        </div>
        <div class="form-group mt-3">
          <label class="mb-3 mt-3">Số lượng </label>
          <input
            type="number"
            class="form-control"
            placeholder="Điền vào số lượng"
            v-model="newBook.quantity"
          />
        </div>

        <div class="form-group mt-3">
          <label class="mb-3 mt-3">Lượt giao dịch </label>
          <input
            type="number"
            class="form-control"
            placeholder="Điền vào số lượng"
            v-model="newBook.transactionVolume"
          />
        </div>

        <div class="form-group mt-3">
          <label class="mb-3 mt-3">Gía bán</label>
          <input
            type="number"
            class="form-control"
            placeholder="Điền vào giá"
            v-model="newBook.price"
          />
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-info mt-3" v-on:click="updateBook(newBook.id)">
          <i class="fa-solid fa-file-pen"></i> Update
        </button>
      </div>
    </div>

    <div class="modal-content" v-if="isDelete">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Xóa sách {{ newBook.id }}</h5>
      </div>
      <div class="modal-footer">
        <button class="btn btn-danger mt-3" v-on:click="deleteBook(newBook.id)">
          <i class="fa-solid fa-trash"></i> Accpect Delete
        </button>
      </div>
    </div>

    <div class="modal-content form-large" v-if="isUploading">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Upload Avatar</h5>
      </div>
      <div class="modal-content">
        <div class="form-group upload mt-3 mb-5">
          <input
            type="file"
            class="form-control"
            v-on:change="handleFileChange"
            accept="image/*"
            ref="fileInput"
            hidden
          />
          <div class="action-upload">
            <input ref="fileInput" type="file" class="hidden" />
            <button class="btn btn-primary mt-3" @click="handleClick">
              <i class="fa-solid fa-image"></i> Chọn ảnh
            </button>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-success mt-3" v-on:click="handleUploadAvatar()">
          <i class="fa-solid fa-upload"></i> Tải lên
        </button>
      </div>
    </div>

    <div class="modal-content form-large" v-if="isUpdateAvatar">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Update Avatar</h5>
      </div>
      <div class="modal-content">
        <div class="form-group upload mt-3 mb-5">
          <input
            type="file"
            class="form-control"
            v-on:change="handleFileChange"
            accept="image/*"
            ref="fileInput"
            hidden
          />
          <div class="action-upload">
            <input ref="fileInput" type="file" class="hidden" />
            <button class="btn btn-primary mt-3" @click="handleClick">
              <i class="fa-solid fa-image"></i> Chọn ảnh
            </button>
          </div>
          <div
            class="alert alert-black alert-preview"
            v-if="newBook.avatar && newBook.avatar.length"
          >
            <img v-bind:src="newBook.avatar" alt="preview" class="preview" />
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-success mt-3" v-on:click="handleUpdateAvatar()">
          <i class="fa-solid fa-upload"></i> Tải lên
        </button>
        <button
          class="btn btn-primary mt-3"
          v-on:click="updateBook(newBook.id)"
        >
          <i class="fa-solid fa-floppy-disk"></i> Save
        </button>
      </div>
    </div>
  </div>

  <Notification ref="notificationRef"></Notification>
</template>

<script lang="ts">
import {
  categoryData,
  bookInsert,
  bookData,
  findBookData,
  Pagination,
} from "../../assets/type/interface"
import Notification from "../notification/Notification.vue"
import { uploadAvatar } from "../../assets/service/uploadFile"
import baseAPI from "../../assets/api/baseAPI"
import { formatMoney } from "../../assets/service/format"

export default {
  components: { Notification },
  data(): {
    selectedFile: File | null
    alert: any
    isDelete: boolean
    isOpenModal: boolean
    dataBook: bookData[] | null
    dataCategory: categoryData[]
    newBook: bookData
    insertBook: bookInsert
    isUploading: boolean
    isUpdateAvatar: boolean
    isCreate: boolean
    findBook: findBookData
    pageOption: Pagination
    isUseEffect: boolean
    classTable: string
    classForm: string
    maxPage: number
  } {
    return {
      classTable: "",
      classForm: "",
      isUseEffect: false,
      findBook: {
        name: "",
        amount: "",
        categoryId: 0,
      },
      pageOption: {
        size: 5,
        index: 1,
        sortByCol: "id",
        sortOption: "desc",
      },
      maxPage: 0,
      isCreate: false,
      isUpdateAvatar: false,
      isUploading: false,
      selectedFile: null,
      alert: null,
      isDelete: false,
      isOpenModal: false,
      dataBook: [],
      dataCategory: [],
      newBook: {
        id: 0,
        name: "",
        categoryId: 0,
        categoryName: "",
        quantity: 0,
        avatar: "",
        price: 0,
        promotionValue: 0,
        transactionVolume: 0,
      },
      insertBook: {
        name: "",
        categoryId: 0,
        quantity: 0,
        avatar: "",
        price: 0,
        promotionValue: 0,
      },
    }
  },

  watch: {
    pageOption: {
      deep: true,
      handler(): void {
        setTimeout(() => {
          this.readBook()
        }, 800)
      },
    },
  },

  mounted() {
    this.alert = this.$refs.notificationRef
  },

  created() {
    this.readBook()
    this.readCategory()
  },

  methods: {
    handleClick() {
      ;(this.$refs.fileInput as HTMLInputElement)?.click()
    },

    formatMoney: formatMoney,
    activeEffect(): void {
      this.isUseEffect = true
      this.classTable = "effect-fly-circle"
      this.classForm = "effect-fly-up"

      setTimeout(() => {
        this.isUseEffect = false
        this.classTable = ""
        this.classForm = ""
      }, 20000)
    },

    waitingUpdate(): void {
      setTimeout(() => {
        this.readBook()
      }, 1000)
    },

    handleFileChange(e: Event): void {
      const target = e.target as HTMLInputElement
      const file = target.files?.[0]
      if (file) {
        this.selectedFile = file
      }
    },
    
    handleOpenInfo(): void {
      this.isOpenModal = true
    },

    clearInput(): void {
      this.insertBook = {
        name: "",
        categoryId: 0,
        quantity: 0,
        avatar: "",
        price: 0,
        promotionValue: 0,
      }
      this.findBook = {
        name: "",
        amount: "",
        categoryId: 0,
      }
      this.newBook = {
        id: 0,
        name: "",
        categoryId: 0,
        categoryName: "",
        quantity: 0,
        avatar: "",
        price: 0,
        promotionValue: 0,
        transactionVolume: 0,
      }
      this.pageOption = {
        size: 5,
        index: 1,
        sortByCol: "id",
        sortOption: "desc",
      }
    },

    handleDeleteBook(id: number): void {
      this.isDelete = true
      this.newBook.id = id
    },

    handleUploading(): void {
      this.isUploading = true
    },

    closeModal(): void {
      this.isOpenModal = false
      this.isDelete = false
      this.isUploading = false
      this.selectedFile = null
      this.isUpdateAvatar = false
    },

    async handleUploadAvatar() {
      if (!this.selectedFile) {
        this.alert.showAlert("warning", "Please select a file")
        return
      }

      const uploadedAvatarUrl = await uploadAvatar(
        this.selectedFile,
        this.alert
      )
      if (uploadedAvatarUrl) {
        this.insertBook.avatar = uploadedAvatarUrl
        this.selectedFile = null
        this.isUploading = false
        this.alert.showAlert("success", "Upload avatar successfully")
      }
    },

    async handleUpdateAvatar() {
      if (!this.selectedFile) {
        this.alert.showAlert("warning", "Please select a file")
        return
      }

      const uploadedAvatarUrl = await uploadAvatar(
        this.selectedFile,
        this.alert
      )
      if (uploadedAvatarUrl) {
        this.newBook.avatar = uploadedAvatarUrl
        this.selectedFile = null
        this.alert.showAlert("success", "Upload update successfully")
      }
    },

    readCategory(): void {
      baseAPI.get(`categories`, this.alert).then((res: any) => {
        this.dataCategory = res.data.data
      })
    },

    readBook(): void {
      if (this.pageOption.index < 1) {
        this.alert.showAlert("warning", "Chỉ số trang phải lớn hơn hoặc bằng 1")
        return
      }

      if (this.pageOption.index > this.maxPage && this.maxPage) {
        this.alert.showAlert("warning", "Bạn đã ở trang cuối cùng")
        return
      }

      const params: any = {
        ...this.pageOption,
        ...this.findBook,
      }

      if (params.name === "") delete params.name
      if (params.amount === "") delete params.amount
      if (params.categoryId === 0) delete params.categoryId

      baseAPI.getByAttribute(`books`, params, this.alert).then((res: any) => {
        this.dataBook = res.data.data
        this.maxPage = res.data.pageDetail.total_pages
      })
    },

    createBook(): void {
      baseAPI.post(`books`, this.insertBook, this.alert).then((res: any) => {
        this.alert.showAlert("success", res.data.message)
        this.clearInput()
        this.waitingUpdate()
      })
    },

    deleteBook(id: number): void {
      baseAPI.delete(`books/${id}`, this.alert).then((res: any) => {
        this.alert.showAlert("success", res.data.message)
        this.isDelete = false
        this.waitingUpdate()
      })
    },

    selectBook(id: number): void {
      this.isOpenModal = true

      baseAPI.get(`books/${id}`, this.alert).then((res: any) => {
        this.newBook = res.data.data
      })
    },

    selectAvatar(id: number): void {
      this.isUpdateAvatar = true

      baseAPI.get(`books/${id}`, this.alert).then((res: any) => {
        this.newBook = res.data.data
      })
    },

    updateBook(id: number): void {
      baseAPI.put(`books/${id}`, this.newBook, this.alert).then((res: any) => {
        this.closeModal()
        this.alert.showAlert("success", res.data.message)
        this.waitingUpdate()
        this.clearInput()
        if (this.isUpdateAvatar) this.isUpdateAvatar = false
      })
    },
  },
}
</script>
