# Agenda 7 - Login Multiusuário

<!--TOC_BEGIN-->
- [Shell](#shell)

<!--TOC_END-->

## Shell

```bash
#__iniciando sistema

# O sistema é iniciado com a senha do admin
#__case init _admin_pass
$init pickachu

#__case adduser _username _senha
$adduser mariana banana
$adduser fabiola supino
$adduser sampaio fofura

$showusers
[ admin mariana fabiola sampaio ]

#__case login e logout
$login mariana capricho
fail: senha incorreta
$login sampaio fofura
$login fabiola supino
fail: ja existe um usuario logado
$logout
$login fabiola supino

$end
```

