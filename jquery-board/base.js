 //全局
 let pageIndex = 0;//當前頁面 
 let pageSize = 2;// 每筆5頁

$(function() {
    onload();
    //載入資料
    getAllData();
    //新增
    $("#addData").click(addData);
    //刪除
    document.addEventListener("click",function(e){
        deleteData(e);
    });
    //查一筆資料
    document.addEventListener("click",function(e){
        viewData(e);
        
    });
    //上一頁
    $('#lastpage').click(function(){
        if(pageIndex>=2){
            pageIndex=pageIndex-pageSize;
            $(".table tbody").empty();
            getAllData();    
        }    
    });

    //下一頁
    $('#nextpage').click(function(){
            pageIndex=pageIndex+pageSize;
            $(".table tbody").empty();
            getAllData();   
    });
    
});


function onload(){
    console.log("window prepared!");
}

function addData(){
    let newBoardData = {};
    newBoardData.title = $("#insertModal #title").val();
    newBoardData.begin = $("#insertModal #releasedate").val();
    newBoardData.end = $("#insertModal #enddate").val();
    newBoardData.user = $("#insertModal #eadministrator").val();
    newBoardData.content = $("#insertModal #exampleFormControlTextarea1").val();
    let requestData = {
        url:"http://127.0.0.1:8080/board/",
        type:'POST',
        data: newBoardData,
    }
    doRequest(requestData).then((data=>{
        $(".table tbody").empty();
        getAllData();  
        // console.log(data);
        // let appendDataHtml = 
        // `<tr id=${data.id}>
        //     <th scope="row">${newBoardData.title}</th>
        //     <td>${newBoardData.begin}</td>
        //     <td>${newBoardData.end}</td>
        //     <td><button type="button" class="btn btn-primary modify" 
        //     data-bs-toggle="modal" data-bs-target="#updateModal">修改</button></td>
        //     <td><button type="button" class="btn btn-primary remove">刪除</button></td>
        // </tr>`
        // $(".table tbody").prepend(appendDataHtml); 
    }))
}

function deleteData(e){
    const target = e.target.closest(".remove");
        if(target){
            const id = target.closest("tr").id;
            let requestData = {
                headers : {crossDomain: true, "Content-Type": "application/json"
            },
                url:'http://127.0.0.1:8080/board/'+id,
                // url:"./board.json",
                type: 'DELETE',
            }
            doRequest(requestData).then((data)=>{
                window.location.reload();
                // target.closest("tr").remove();
            })
        }
}

function viewData(e){
    const target = e.target.closest(".modify");
    if(target){
        const id = target.closest("tr").id;
        getOneData(id).then(data=>{
            $("#updateModal #title").val(data.title);
            $("#updateModal #releasedate").val(data.begin);
            $("#updateModal #enddate").val(data.end);
            $("#updateModal #eadministrator").val(data.user);
            $("#updateModal #exampleFormControlTextarea1").val(data.content);

        });

        document.addEventListener('click',function(e){
            modifyData(e,id);
        });
        // let requestData = {}
        // requestData.title = $("#updateModal #title").val();
        // requestData.begin = $("#updateModal #releasedate").val();
        // requestData.end = $("#updateModal #enddate").val();
        // requestData.user =$("#updateModal #eadministrator").val();
        // requestData.content = $("#updateModal #exampleFormControlTextarea1").val();
        // console.log("requestData:" + requestData);

        // doRequest(requestData).then(data=>{
        //     console.log("success");
        // })
    }
}

function modifyData(e,id){
    const target = e.target.closest(".edit");
    if(target){
        let updatedData = {};
        updatedData.id = id;
        updatedData.title = $("#updateModal #title").val();
        updatedData.begin = $("#updateModal #releasedate").val();
        updatedData.end = $("#updateModal #enddate").val();
        updatedData.user =$("#updateModal #eadministrator").val();
        updatedData.content = $("#updateModal #exampleFormControlTextarea1").val();
        console.log(updatedData);
        let requestData = {
            url:"http://127.0.0.1:8080/board/",
            data:updatedData,
            type:"PUT"
            
        }
        doRequest(requestData).then((res)=>{
            console.log("success");
            window.location.reload();

        })  
    }
}

function getOneData(id){
    return new Promise(resolve=>{
        let requestData ={
            url:"http://127.0.0.1:8080/board/" + id,
            type :"GET"
        }
        doRequest(requestData).then(data=>{
            resolve(data);
        });
    })
    
}



function getAllData(){
    let someData = {};
    someData.firstResult=pageIndex;
    someData.maxResult=pageSize;
        requestData = {
            // url:"./board.json",
            url:"http://127.0.0.1:8080/board/",
            type : "GET",
            data:someData

        }
        doRequest(requestData)
        .then((data)=>{
            let loadedElementHtml="";
            data.forEach((element)=>{
                loadedElementHtml +=
                `<tr id=${element.id}>
                <th scope="row">${element.title}</th>
                <td>${element.begin}</td>
                <td>${element.end}</td>
                <td><button type="button" class="btn btn-primary modify" 
                data-bs-toggle="modal" data-bs-target="#updateModal">修改</button></td>
                <td><button type="button" class="btn btn-primary remove">刪除</button></td>
                </tr>`
            });
            $(".table tbody").append(loadedElementHtml);
        })
        .catch((error)=>{
            console.log(error);
        })
}



function doRequest(requestData){
    return new Promise((resolve,reject)=>{
        $.ajax({
            headers:requestData.headers,
            url:requestData.url,
            type:requestData.type,
            data:requestData.data,
            datatype:"json",
            success: function(response){
                resolve(response);
            },
            error:function(error){
                reject(error);
            }
        });
    })
}