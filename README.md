# GenshinArtifactSimulator
《原神》圣遗物系统模拟实现


目录结构图
src/main/java
│  
├─enums   									//游戏相关数据和枚举  
│  │  CategoryEnum.java						//圣遗物部位枚举  
│  │  ChanceEnum.java						//接口，用于区分一个Enum是否带权的  
│  │  DomainEnum.java						//副本相关数据  
│  │  ExpEnum.java							//圣遗物经验相关数据  
│  │  StarEnum.java							//圣遗物星级枚举  
│  │  SuitEnum.java							//圣遗物套装名称枚举  
│  │  UpMultiEnum.java						//圣遗物强化相关数据  
│  │    
│  └─attributes								//圣遗物主副属性相关数据  
│          PrimAttrCircletEnum.java			//理之冠主词条分布  
│          PrimAttrEnum.java				//接口，用于区分是否是主词条  
│          PrimAttrFeatherEnum.java			//死之羽主词条分布  
│          PrimAttrFlowerEnum.java			//生之花主词条分布  
│          PrimAttrGobletEnum.java			//空之杯主词条分布  
│          PrimAttrSandEnum.java			//时之沙主词条分布  
│          PrimAttrUpEnum.java				//主词条成长相关数据  
│          SubAttrEnum.java					//副词条分布  
│          SubAttrUpEnum.java				//副词条成长相关数据  
│          
├─model										
│      Artifact.java						//圣遗物类  
│      ArtifactEvaluator.java				//圣遗物评价器  
│      ArtifactGroupDisplayer.java			//圣遗物展示工具（debug用）  
│      Domain.java							//副本类  
│      Player.java							//玩家类  
│      
└─utils  
        Utils.java							//工具类  
        
