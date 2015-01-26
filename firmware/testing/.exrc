if &cp | set nocp | endif
let s:cpo_save=&cpo
set cpo&vim
imap <F6> mzA	:set fileformat=unix:set endofline:%s/	/    /g:%s/ *$//:nohlsearchi`za
nmap gx <Plug>NetrwBrowseX
nnoremap <silent> <Plug>NetrwBrowseX :call netrw#NetrwBrowseX(expand("<cWORD>"),0)
map <S-F7> 80|F s	
map <F7> 80|F s
map <F6> mzA	:set fileformat=unix:set endofline:%s/	/    /g:%s/ *$//:nohlsearchi`z
abbr cmain intmain(int argc, char *argv[]){return 0;<BS>}kkO
let &cpo=s:cpo_save
unlet s:cpo_save
set autoindent
set backspace=1
set cindent
set cinoptions=:0,p0,t0
set cinwords=if,unless,else,while,until,do,for,switch,case
set comments=s1:/*,mb:*,ex:*/,://
set expandtab
set fileencodings=ucs-bom,utf-8,default,latin1
set formatoptions=clqro
set helplang=en
set incsearch
set laststatus=2
set modelines=0
set ruler
set shiftround
set shiftwidth=3
set showcmd
set showmatch
set smarttab
set softtabstop=3
set statusline=%f%m%r%h%w\ %R%M\ [POS=%04l,%04v][%p%%]\ [LEN=%L]
set title
set wildmenu
set window=0
" vim: set ft=vim :
