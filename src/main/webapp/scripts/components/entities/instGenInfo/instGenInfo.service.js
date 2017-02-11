'use strict';

angular.module('stepApp')
    .factory('InstGenInfo', function ($resource, DateUtils) {
        return $resource('api/instGenInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    /*console.log(data);
                    data = angular.fromJson(data);
                    data.publicationDate = DateUtils.convertLocaleDateFromServer(data.publicationDate);
                    return data;*/
                    if(!data){
                       data={id:0};
                    }
                    return data = angular.fromJson(data);
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocaleDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {

                    if(angular.isUndefined(data.publicationDate)){
                        data.publicationDate = DateUtils.convertLocaleDateToServer(data.publicationDate);
                        data.firstRecognitionDate = DateUtils.convertLocaleDateToServer(data.firstRecognitionDate);
                        data.firstAffiliationDate = DateUtils.convertLocaleDateToServer(data.firstAffiliationDate);
                        data.lastExpireDateOfAffiliation = DateUtils.convertLocaleDateToServer(data.lastExpireDateOfAffiliation);
                        data.dateOfMpo = DateUtils.convertLocaleDateToServer(data.dateOfMpo);
                    }
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstGenInfoAllApprove', function ($resource, DateUtils) {
        return $resource('api/instGenInfos/approveAllInfo', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocaleDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
