package com.sysco;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class JsonSchemaWriter {

    JsonSchemaGenerator jsonSchemaGenerator;
    {
        jsonSchemaGenerator = new JsonSchemaGenerator(new ObjectMapper());
    }

    public void writeSchemaToFile(String filepath, Class classType) throws IOException {
        JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(classType);
        String jsonSchemaAsString = new ObjectMapper().writeValueAsString(jsonSchema);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
        try{
            bufferedWriter.write(jsonSchemaAsString);
        }catch (IOException e){
            throw new RuntimeException("Unable to write file", e);
        }finally {
            bufferedWriter.close();
        }
    }
}
