set nocompatible              " be iMproved, required
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()
" alternatively, pass a path where Vundle should install plugins
"call vundle#begin('~/some/path/here')

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

" The following are examples of different formats supported.
" Keep Plugin commands between vundle#begin/end.
" plugin on GitHub repo
Plugin 'tpope/vim-fugitive'

" plugin from http://vim-scripts.org/vim/scripts.html
Plugin 'git://git.wincent.com/command-t.git'

" git repos on your local machine (i.e. when working on your own plugin)
Plugin 'rstacruz/sparkup', {'rtp': 'vim/'}

" Install L9 and avoid a Naming conflict if you've already installed a
" different version somewhere else.
Plugin 'ascenator/L9', {'name': 'newL9'}

" You complete me plugin
Plugin 'Valloric/YouCompleteMe'

" All of the colorschemes
Plugin 'flazz/vim-colorschemes'

" Advanced cpp syntax highlighting
Plugin 'octol/vim-cpp-enhanced-highlight'

" Tagbar for sidebar viewing of code
Plugin 'majutsushi/tagbar'

" Awesome colorscheme
Plugin 'ayu-theme/ayu-vim'

" Shows small indentation lines
Plugin 'Yggdroot/indentLine'

" Allow multiple tabs per window
Plugin 'jeetsukumaran/vim-buffergator'
" Plugin 'zefei/vim-wintabs'

" Addon to help show the current buffers
Plugin 'ap/vim-buftabline'

" Shift buffers over
Plugin 'kien/ctrlp.vim'

" Autoload scripts
Plugin 'xolox/vim-misc'

" Save sessions
Plugin 'xolox/vim-session'

" Syntax checking and statuslines
" Plugin 'scrooloose/syntastic'

" All of your Plugins must be added before the following line
call vundle#end()            " required
filetype plugin indent on    " required

let g:cpp_class_scope_highlight = 1
let g:cpp_experimental_simple_template_highlight = 1
let g:cpp_experimental_template_highlight = 1
let g:cpp_concepts_highlight = 1

" YouCompleteMe
let g:ycm_confirm_extra_conf = 1
let g:ycm_global_ycm_extra_conf = "/home/smerkous/ML/mlcapture/.ycm_extra_conf.py"
"/home/smerkous/Documents/halite/.ycm_extra_conf.py"

" Indent line options
" let g:indentLine_char = ''
" let g:indentLine_first_char = ''
let g:indentLine_char = '▏'
let g:indentLine_showFirstIndentLevel = 0
let g:indentLine_setColors = 0
let g:indentLine_enabled = 1

" Fix tab issues
set number
set tabstop=4 autoindent
set shiftwidth=4

" Move across tabs easier
" map <C-H> <Plug>(wintabs_previous)
" map <C-L> <Plug>(wintabs_next)
" map <C-W>c <Plug>(wintabs_close_window)
" map <C-W>o <Plug>(wintabs_only_window)
" command! Tabc WintabsCloseVimtab
" command! Tabo WintabsOnlyVimtab
let g:wintabs_display = "statusline"

" Autosave sessions
let g:session_autosave = 'no'
let g:session_autoload = 'no'

" Setup some default ignores
let g:ctrlp_custom_ignore = {
  \ 'dir':  '\v[\/](\.(git|hg|svn|CMakeFiles)|\_site)$',
  \ 'file': '\v(MyBot|compile_commands\.json|cmake_install.*)|\.(exe|so|dll|class|png|jpg|jpeg|MD)$',
\}

" Use the nearest .git directory as the cwd
" This makes a lot of sense if you are working on a project that is in version
" control. It also supports works with .svn, .hg, .bzr.
let g:ctrlp_working_path_mode = 'r'

" Use a leader instead of the actual named binding
nmap <leader>p :CtrlP<cr>

" Easy bindings for its various modes
nmap <leader>bb :CtrlPBuffer<cr>
nmap <leader>bm :CtrlPMixed<cr>
nmap <leader>bs :CtrlPMRU<cr>

" Use the right side of the screen
let g:buffergator_viewport_split_policy = 'R'

" I want my own keymappings...
let g:buffergator_suppress_keymaps = 1

" Looper buffers
"let g:buffergator_mru_cycle_loop = 1

" Go to the previous buffer open
nmap <leader>jj :BuffergatorMruCyclePrev<cr>
nnoremap <C-Left> :BuffergatorMruCyclePrev<cr>

" Go to the next buffer open
nmap <leader>kk :BuffergatorMruCycleNext<cr>
nnoremap <C-Right> :BuffergatorMruCycleNext<cr>

" View the entire list of buffers open
nmap <leader>bl :BuffergatorOpen<cr>

" Shared bindings from Solution #1 from earlier
nmap <leader>T :enew<cr>
nmap <leader>bq :bp <BAR> bd #<cr>


" Use the nearest .git directory as the cwd
" This makes a lot of sense if you are working on a project that is in version
" control. It also supports works with .svn, .hg, .bzr.
let g:ctrlp_working_path_mode = 'r'

" Use a leader instead of the actual named binding
nmap <leader>p :CtrlP<cr>

" Easy bindings for its various modes
nmap <leader>bb :CtrlPBuffer<cr>
nmap <leader>bm :CtrlPMixed<cr>
nmap <leader>bs :CtrlPMRU<cr>


" Open a terminal in a new pane
nnoremap <C-T> :ConqueTermSplit bash<CR>

" Easily resize panes
call tinymode#EnterMap("winsize", "<C-W>+", "+")
call tinymode#EnterMap("winsize", "<C-W>-", "-")
call tinymode#EnterMap("winsize", "<C-W><", "<")
call tinymode#EnterMap("winsize", "<C-W>>", ">")
call tinymode#Map("winsize", "+", "wincmd +")
call tinymode#Map("winsize", "-", "wincmd -")
call tinymode#Map("winsize", "<", "wincmd >")
call tinymode#Map("winsize", ">", "wincmd <")
call tinymode#ModeMsg("winsize", "Change window size (vert) +/- (horz) </>") 
call tinymode#ModeArg("winsize", "timeoutlen", 3000)

" Copy and paste to and from the global clipboard
set clipboard=unnamedplus

" Allow multiple files per pane
set hidden

" Fix the backspace to allow multi-line support
set backspace=indent,eol,start

" Switch between buffers easier
" nnoremap <C-N> :bnext<CR>
" nnoremap <C-P> :bprev<CR>

" Set line identifiers
set list lcs=tab:\|\ 

" Colorscheme
set termguicolors
syntax enable
" let g:solarized_use16 = 1
" let g:solarized_term_italics = 1
let ayucolor="dark"
let &t_8f = "\<Esc>[38;2;%lu;%lu;%lum"
let &t_8b = "\<Esc>[48;2;%lu;%lu;%lum"
set background=dark
colorscheme ayu " solarized8 -- DevC++

" To ignore plugin indent changes, instead use:
"filetype plugin on
"
" Brief help
" :PluginList       - lists configured plugins
" :PluginInstall    - installs plugins; append `!` to update or just :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unused plugins; append `!` to auto-approve removal
"
" see :h vundle for more details or wiki for FAQ
" Put your non-Plugin stuff after this line
