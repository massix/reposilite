<template lang="pug">
    form(method="put").flex.flex-col.items-center.pb-3
        h1.pb-3.font-bold Upload artifact
        select#repositories(name="repository" v-model="repository" placeholder="repository" required).w-96.text-center.p-1.m-1
            option(v-for="repo in this.$parent.auth.repositories" :value="repo") {{ repo }}
        input(name="groupId" v-model="groupId" placeholder="groupId" required).p-1.m-1.w-96.text-center
        input(name="artifactId" v-model="artifactId" placeholder="artifactId" required).p-1.m-1.w-96.text-center
        input(name="version" v-model="version" placeholder="version" required).p-1.m-1.w-96.text-center
        div(v-for="(file, index) in files" :key="file.id").p-1.m-1
            i.fas.fa-file.mr-2
            span {{ file.name }}
            span(v-if="file.error") {{ file.error }}
            span(v-else-if="file.success") {{ file.success }}
        FileUpload.my-2.bg-gray-200.border-dashed.border-gray-500.rounded.border.w-96.h-9.pt-1(
            v-model="files"
            ref="upload"
            :drop="true"
            :multiple="true"
        ) Select or drop files
        button(name="submit" type="submit" v-on:click="upload").w-96.p-1.m-1.bg-white.cursor-pointer.border Upload
        notifications(group="upload" position="center top")
</template>

<script>
import FileUpload from 'vue-upload-component'

export default {
  data () {
    return {
      files: [],
      repository: this.$parent.auth.repositories[0],
      groupId: '',
      artifactId: '',
      version: ''
    }
  },
  components: {
    FileUpload
  },
  methods: {
    upload (event) {
      const artifact = `${this.repository}/${this.groupId.replace('.', '/')}/${
        this.artifactId
      }/${this.version}/`

      for (const vueFile of this.files) {
        const auth = this.$parent.auth
        const reader = new FileReader()

        reader.addEventListener('load', event => {
          this.$http
            .put(this.url() + artifact + vueFile.name, event.target.result, {
              auth: {
                username: auth.alias,
                password: auth.token
              }
            })
            .then(() =>
              this.$notify({
                group: 'upload',
                type: 'success',
                title:
                  'File ' + vueFile.name + ' has been uploaded successfully'
              })
            )
            .catch(err => {
              this.error = err
              this.$notify({
                group: 'upload',
                type: 'error',
                title: 'Cannot upload file ' + vueFile.name,
                text: err.status + ': ' + err.message
              })
            })
        })

        reader.readAsBinaryString(vueFile.file)
        event.preventDefault()
      }
    }
  }
}
</script>

<style lang="stylus">
#file
    cursor pointer
</style>
