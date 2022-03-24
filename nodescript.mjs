import fs from 'fs'
import path from 'path'
const filePath = path.join(process.cwd(), "./pubspec.yaml")
console.log(filePath)
  fs.readFile(filePath, 'utf-8', (err, data) => {
    if (err) throw err;
    var newData =  data.replace(/version: (\d+).(\d+).(\d+)/, (a,b,c,d)=>{ 
        var newVersion =  parseInt(d) + 1 
        return `version: ${b}.${c}.${newVersion}`
    }) 
  
    fs.writeFileSync(filePath, newData,{encoding:'utf8',flag:'w+'})  
});


