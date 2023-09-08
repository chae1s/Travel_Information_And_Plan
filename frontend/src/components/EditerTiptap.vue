<template>
    <div id="text-editor">
        <link href="https://cdn.jsdelivr.net/npm/remixicon@2.2.0/fonts/remixicon.css" rel="stylesheet">
        <div class="toolbar" v-if="editor">
            <div class="align-dropdown">
                <button class="dropbtn">
                    Heading ▼
                </button>
                <div class="dropdown-content">
                    <a
                            v-for="index in 6"
                            :class="{ 'active': editor.isActive('heading', { level: index }) }"
                            :style="{ fontSize: (20 - index) + 'px' }"
                            @click="onHeadingClick(index)"
                            role="button">
                        H{{ index }}
                    </a>
                </div>
            </div>

            <button
                    v-for="{ slug, option, active, icon }, index in textActions"
                    :class="{ 'active': editor.isActive(active) }"
                    @click="onActionClick(slug, option)">
                <i :class="icon"></i>
            </button>
        </div>

        <editor-content :editor="editor" />

        <div v-if="editor" class="footer">
      <span class="characters-count" :class="maxLimit ? limitWarning : ''">
        {{ charactersCount }} {{ maxLimit ? `/ ${maxLimit} characters` : 'characters' }}
      </span>
            |
            <span class="words-count">
        {{ wordsCount }} words
      </span>
        </div>
    </div>
</template>

<script>
import { Editor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import TextAlign from '@tiptap/extension-text-align';
import Underline from '@tiptap/extension-underline';
import Subscript from '@tiptap/extension-subscript';
import Superscript from '@tiptap/extension-superscript';
import CharacterCount from '@tiptap/extension-character-count';
import Images from '@tiptap/extension-image';
import { uploadImage } from '@/api/index';

export default {
    components: {
        EditorContent,
    },
    props: {
        modelValue: {
            type: String,
            default: '',
        },
        maxLimit: {
            type: Number,
            default: null,
        },
    },
    data() {
        return {
            editor: null,
            textActions: [
                { slug: 'bold', icon: 'ri-bold', active: 'bold' },
                { slug: 'italic', icon: 'ri-italic', active: 'italic' },
                { slug: 'underline', icon: 'ri-underline', active: 'underline' },
                { slug: 'strike', icon: 'ri-strikethrough', active: 'strike' },
                { slug: 'align', option: 'left', icon: 'ri-align-left', active: { textAlign: 'left' } },
                { slug: 'align', option: 'center', icon: 'ri-align-center', active: { textAlign: 'center' } },
                { slug: 'align', option: 'right', icon: 'ri-align-right', active: { textAlign: 'right' } },
                { slug: 'align', option: 'justify', icon: 'ri-align-justify', active: { textAlign: 'justify' } },
                { slug: 'bulletList', icon: 'ri-list-unordered', active: 'bulletList' },
                { slug: 'orderedList', icon: 'ri-list-ordered', active: 'orderedList' },
                { slug: 'subscript', icon: 'ri-subscript-2', active: 'subscript' },
                { slug: 'superscript', icon: 'ri-superscript-2', active: 'superscript' },
                { slug: 'undo', icon: 'ri-arrow-go-back-line', active: 'undo' },
                { slug: 'redo', icon: 'ri-arrow-go-forward-line', active: 'redo' },
                { slug: 'clear', icon: 'ri-format-clear', active: 'clear' },
            ],
        }
    },
    computed: {
        charactersCount() {
            return this.editor.storage.characterCount.characters();
        },
        wordsCount() {
            return this.editor.storage.characterCount.words();
        },
        limitWarning() {
            const isCloseToMax = this.charactersCount >= this.maxLimit - 20;
            const isMax = this.charactersCount === this.maxLimit;

            if (isCloseToMax && !isMax) return 'warning';
            if (isMax) return 'danger';

            return '';
        },
    },
    watch: {
        modelValue(value) {
            if (this.editor.getHTML() === value) return;
            this.editor.commands.setContent(this.modelValue, false);
        },
    },
    methods: {
        onActionClick(slug, option = null) {
            const vm = this.editor.chain().focus();
            const actionTriggers = {
                bold: () => vm.toggleBold().run(),
                italic: () => vm.toggleItalic().run(),
                underline: () => vm.toggleUnderline().run(),
                strike: () => vm.toggleStrike().run(),
                bulletList: () => vm.toggleBulletList().run(),
                orderedList: () => vm.toggleOrderedList().run(),
                align: () => vm.setTextAlign(option).run(),
                subscript: () => vm.toggleSubscript().run(),
                superscript: () => vm.toggleSuperscript().run(),
                undo: () => vm.undo().run(),
                redo: () => vm.redo().run(),
                clear: () => {
                    vm.clearNodes().run();
                    vm.unsetAllMarks().run();
                },
            };

            actionTriggers[slug]();
        },
        onHeadingClick(index) {
            const vm = this.editor.chain().focus();
            vm.toggleHeading({ level: index }).run()
        }
    },
    mounted() {
        this.editor = new Editor({
            content: this.modelValue,
            extensions: [
                StarterKit,
                Underline,
                Subscript,
                Superscript,
                CharacterCount.configure({
                    limit: this.maxLimit,
                }),
                TextAlign.configure({
                    types: ['heading', 'paragraph'],
                }),
                Images,
            ],
            onUpdate: () => {
                this.$emit('update:modelValue', this.editor.getHTML());
            },
            editorProps: {
                handleDrop: function(view, event, slice, moved) {
                    if (!moved && event.dataTransfer && event.dataTransfer.files && event.dataTransfer.files[0]) {
                        let file = event.dataTransfer.files[0];
                        let filesize = ((file.size/1024)/1024).toFixed(4);
                        if ((file.type === "image/jpeg" || file.type === "image/png") && filesize < 10) {
                            let _URL = window.URL || window.webkitURL;
                            let img = new Image();
                            img.src = _URL.createObjectURL(file);
                            img.onload = function () {
                                if (this.width > 5000 || this.height > 5000) {
                                    window.alert("이미지가 5000 픽셀 이상입니다.");
                                } else {
                                    const data = new FormData();
                                    data.append('image', file);

                                    uploadImage(data).then(function(response) {
                                        console.log(response);
                                        let image = new Image();
                                        image.src = response.data;
                                        image.onload = function() {
                                            // 드랍한 위치에 이미지 삽입
                                            const {schema} = view.state;
                                            const coordinates = view.posAtCoords({
                                                left: event.clientX,
                                                top: event.clientY
                                            });
                                            const node = schema.nodes.image.create({src: response.data}); // img 요소 생성
                                            const transaction = view.state.tr.insert(coordinates.pos, node); // 특정 위치에 요소 삽입
                                            return view.dispatch(transaction);
                                        }
                                    }).catch(function(error) {
                                        if (error) {
                                            window.alert("이미지를 업로드하는데 문제가 생겼습니다.");
                                        }
                                    });
                                }
                            };
                        } else {
                            window.alert("이미지는 jpg나 png 형식의이고 10Mb 미만의 크기만 업로드 가능합니다");
                        }
                        return true;
                    }
                    return false;
                }
            },

        });
    },
    beforeUnmount() {
        this.editor.destroy();
    },
}
</script>

<style lang="less" scoped>
#text-editor {
  border: 1px solid #808080;

  .toolbar {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    border-bottom: 1px solid #808080;

    > button {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 32px;
      height: 32px;
      font-size: 20px;
      background: #fff;
      color: #333;
      border: none;
      border-radius: 2px;
      margin: 0.5em 4px;
      -webkit-appearance: none;
      cursor: pointer;

      &.active {
        background: #333;
        color: #fff;
      }
    }
  }

  .align-dropdown {
    position: relative;
    display: inline-block;
    margin: 0.5em 8px;

    > button {
      height: 32px;
      background: #fff;
      color: #333;
      border: none;
      border-radius: 2px;
      -webkit-appearance: none;
      cursor: pointer;
    }

    > .dropdown-content {
      display: none;
      position: absolute;
      left: 0;
      right: 0;
      border: 1px solid #333;
      outline: 1px solid #fff;
      border-radius: 2px;
      background-color: #fff;
      z-index: 1;

      a {
        display: block;
        padding: 6px 12px;
        text-align: center;
        cursor: pointer;

        &:hover, &.active {
          background: #333;
          color: #fff;
        }
      }
    }

    &:hover .dropdown-content {
      display: block;
    }
  }

  .divider {
    width: 1px;
    height: 24px;
    background: #333;
    margin-right: 6px;
  }

  .footer {
    color: #808080;
    font-size: 14px;
    text-align: right;
    padding: 6px;

    .characters-count {
      &.warning {
        color: orange;
      }

      &.danger {
        color: red;
      }
    }
  }

  .tiptap {
    height: 500px;
    overflow-y: auto;
    padding-left: 0.5em;
    padding-right: 0.5em;
    outline: none;

    > p:first-child {
      margin-top: 0.5em;
    }

    > h1, h2, h3, h4, h5, h6 {
      &:first-child {
        margin-top: 0.5em;
      }
    }

  }
}
</style>