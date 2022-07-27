<template>
  <div class="login-container">
    <!--div class="login-container-logo">
      <img class="login-container-logo-img" src="@/assets/login/login_logo.png"/>
    </div-->
    <div class="login-container-form">
      <div class="login-container-hello">欢 迎 登 录</div>
      <div class="login-container-input">
        <a-form ref="formRef" :model="form" :rules="rules" @keyup.enter="handleSubmit">
          <a-form-item name="username">
            <a-input v-model:value="form.username" autocomplete="off" placeholder="请输入账号">
              <template v-slot:prefix>
                <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item name="password">
            <a-input-password v-model:value="form.password" autocomplete="off" placeholder="请输入密码">
              <template v-slot:prefix>
                <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
              </template>
            </a-input-password>
          </a-form-item>
          <a-form-item name="verifyCode">
            <div style="display: flex;">
              <!--@ts-ignore-->
              <a-input :maxlength="4" v-model:value="form.verifyCode" autocomplete="off" placeholder="请输入验证码"/>
              <div @click="refreshVerifyCode" style="cursor: pointer;">
                <div style="width: 150px; height: 35px;">
                  <img v-if="verifyCode && verifyCode.image" :src="verifyCode.image"/>
                  <div v-else style="height: 35px; line-height: 35px; text-align: center;">
                    <a-spin/>
                  </div>
                </div>
              </div>
            </div>
          </a-form-item>
          <a-form-item>
            <a-checkbox v-model:checked="form.remember">记住密码</a-checkbox>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleSubmit()">
              登录
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {useStore} from 'vuex'
import {useRouter} from 'vue-router'
import {message} from 'ant-design-vue'
import {LoginFrom} from '@/types/views/login'
import {computed, defineComponent, reactive, ref, unref, UnwrapRef} from "vue"
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue'

export default defineComponent({
  name: "login",
  components: {
    UserOutlined,
    LockOutlined
  },
  setup() {

    // 表单数据
    const form = data_form()
    const rules = reactive({
      username: [
        {
          required: true,
          message: '请输入账号',
          trigger: 'change'
        }
      ],
      password: [
        {
          required: true,
          message: '请输入密码',
          trigger: 'change'
        }
      ],
      verifyCode: [
        {
          required: true,
          message: '请输入验证码',
          trigger: 'change'
        }
      ],
    })
    const formRef = ref()
    const store = useStore()
    const router = useRouter()
    const verifyCode = ref<{ image: string, captcha: string }>({
      captcha: '',
      image: '',
    });

    return {
      formRef,
      store,
      router,
      form,
      rules,
      verifyCode,
    }
  },
  methods: {
    refreshVerifyCode() {
      this['$daisyRequest'].get(`/general/user/verify_code`).then((response: any) => {
        const data = response.data.data;
        if (!data) {
          this['$message'].error('请求异常');
          return;
        }
        this.verifyCode.image = data.image;
        this.verifyCode.captcha = data.captcha;
      }, (error: any) => {
        this['$message'].error(error);
      });
    },
    handleSubmit() {
      this.formRef.validate().then(() => {
        this.form.captcha = this.verifyCode.captcha;
        this.store.dispatch('user/login', this.form).then(e => {
          const route = this.router.currentRoute.value
          const url = route.query.redirect || '/'
          this.router.push(url as string)
        }).catch(err => {
          message.error(err.message || err.data.message);
          this.refreshVerifyCode();
        });
      })
    },
  },
  mounted() {
    this.refreshVerifyCode();
  },
});

function data_form() {
  const form: UnwrapRef<LoginFrom> = reactive({
    username: '',
    password: '',
    verifyCode: '',
    captcha: '',
    remember: false,
  })
  return form
}
</script>

<style lang="scss" scoped>
.login-container {
  position: fixed;
  width: 100%;
  height: 100%;
  background: url('/src/assets/login/login_bg.jpg');
  background-size: cover;
  overflow: hidden;

  & .login-container-logo {
    position: absolute;
    width: 377px;
    height: 468px;
    top: calc((100% - 468px) / 2);
    left: calc((100% - 836px) / 2);
    background-color: #ffffff;
    opacity: 50%;

    & .login-container-logo-img {
      display: inline-block;
      position: absolute;
      left: calc((100% - 216px) / 2);
      top: calc((100% - 108px) / 2);
    }
  }

  & .login-container-form {
    position: absolute;
    width: 459px;
    height: 468px;
    top: calc((100% - 468px) / 2);
    left: calc((100% - 836px) / 2 + 377px);
    background-color: #ffffff;

    & .login-container-hello {
      width: 110px;
      height: 23px;
      overflow-wrap: break-word;
      color: rgba(24, 144, 255, 1);
      font-size: 24px;
      // font-family: PingFangSC-Medium;
      white-space: nowrap;
      line-height: 24px;
      margin-left: calc((100% - 110px) / 2);
      margin-top: 50px;
    }

    & .login-container-input {
      //margin-top: 40px;
      padding: 40px;
      font-size: 16px;
      color: #fff;
    }
  }

  .login-container-tips {
    position: fixed;
    bottom: 10px;
    width: 100%;
    height: 40px;
    color: rgba(255, 255, 255, 0.856);
    text-align: center;
  }
}
</style>
<style lang="scss">
.login-container {
  & .login-container-form {
    & .ant-col {
      width: 100%;
      padding: 0 10px 0 10px;
    }

    & .ant-input {
      height: 35px;
    }

    & .ant-btn {
      width: 100%;
      height: 40px;
      font-size: 18px;
      font-weight: normal;
      //margin-top: 10px;
    }

    & .ant-form-item-explain-error {
      color: #faad14;
    }

    & .ant-form-item-has-error {
      border-color: #faad14;
    }
  }
}
</style>
