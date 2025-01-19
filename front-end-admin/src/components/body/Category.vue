<template>
  <h3 class="text-center mt-5 mb-5 large">
    Quản lý danh mục
    <i
      class="fas fa-list ml-2"
      v-on:click="isCreate = !isCreate"
      title="Create category"
    ></i>
  </h3>

  <div class="alert alert-form" v-if="isCreate">
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Tên danh mục</label>
      <input
        type="text"
        class="form-control"
        placeholder="Điền vào tên danh mục"
        v-model="insertCategory.name"
      />
    </div>
    <div class="row item-right mt-5">
      <button class="btn btn-success mb-3" v-on:click="createCategory()">
        <i class="fa-solid fa-floppy-disk"></i> Save
      </button>
    </div>
  </div>

  <table class="table table-dark mt-5">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Tên danh mục</th>
        <th scope="col">Action</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-if="dataCategory && dataCategory.length"
        v-for="(v, k) in dataCategory"
        :key="k"
      >
        <th scope="row">{{ k + 1 }}</th>
        <td>{{ v.name }}</td>
        <td>
          <button
            class="btn btn-success mr-2"
            v-on:click="selectCategory(v.id)"
          >
            <i class="fas fa-edit"></i>
          </button>
          <button class="btn btn-danger" v-on:click="handleDelete(v.id)">
            <i class="fas fa-trash-alt"></i>
          </button>
        </td>
      </tr>
      <tr v-else>
        <td colspan="3" class="text-center">
          <i class="fa-solid fa-spinner"></i> No Data Available
        </td>
      </tr>
    </tbody>
  </table>

  <div class="modal">
    <div class="modal-content" v-if="isOpenModal">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Update danh mục</h5>
      </div>
      <div class="modal-body">
        <div class="form-group mt-3">
          <input
            type="text"
            class="form-control"
            placeholder="Điền vào tên danh mục"
            v-model="newCategory.name"
          />
        </div>
      </div>
      <div class="modal-footer">
        <button
          class="btn btn-info mt-3"
          v-on:click="updateCategory(newCategory.id)"
        >
          <i class="fa-solid fa-file-pen"></i> Update
        </button>
      </div>
    </div>
    <div class="modal-content" v-if="isDelete">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Xóa danh mục {{ newCategory.id }}</h5>
      </div>
      <div class="modal-footer">
        <button
          class="btn btn-danger mt-3"
          v-on:click="deleteCategory(newCategory.id)"
        >
          <i class="fa-solid fa-trash"></i> Accpect Delete
        </button>
      </div>
    </div>
  </div>

  <Notification ref="notificationRef"></Notification>
</template>

<script lang="ts">
import axios from "axios"
import { categoryInsert, categoryData } from "../../assets/type/interface"
import Notification from "../notification/Notification.vue"
import baseAPI from "../../assets/api/baseAPI"

export default {
  components: { Notification },
  data(): {
    isOpenModal: boolean
    dataCategory: categoryData[] | null
    insertCategory: categoryInsert
    newCategory: categoryData
    alert: any | null
    isDelete: boolean
    isCreate: boolean
  } {
    return {
      isCreate: false,
      alert: null,
      isDelete: false,
      isOpenModal: false,
      dataCategory: [],
      insertCategory: {
        name: "",
      },
      newCategory: {
        id: 0,
        name: "",
      },
    }
  },
  mounted() {
    this.alert = this.$refs.notificationRef
  },
  created() {
    this.readCategory()
  },
  methods: {
    clearInput(): void {
      this.insertCategory.name = ""
    },
    closeModal(): void {
      this.isDelete = false
      this.isOpenModal = false
    },
    handleDelete(id: number): void {
      this.isDelete = true
      this.newCategory.id = id
    },

    readCategory(): void {
      baseAPI.get(`categories`, this.alert).then((res: any) => {
        this.dataCategory = res.data.data
      })
    },

    createCategory(): void {
      baseAPI
        .post(`categories`, this.insertCategory, this.alert)
        .then((res: any) => {
          this.alert.showAlert("success", res.data.message)
          this.clearInput()
          this.readCategory()
        })
    },

    deleteCategory(id: number): void {
      baseAPI.delete(`categories/` + id, this.alert).then((res: any) => {
        this.alert.showAlert("success", res.data.message)
        this.readCategory()
        this.closeModal()
      })
    },

    selectCategory(id: number): void {
      this.isOpenModal = true

      baseAPI.get(`categories/${id}`, this.alert).then((res: any) => {
        this.newCategory = res.data.data
      })
    },

    updateCategory(id: number): void {
      axios
      baseAPI
        .put(`categories/${id}`, this.newCategory, this.alert)
        .then((res: any) => {
          this.alert.showAlert("success", res.data.message)
          this.readCategory()
          this.closeModal()
        })
    },
  },
}
</script>
