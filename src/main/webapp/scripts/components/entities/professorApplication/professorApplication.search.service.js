'use strict';

angular.module('stepApp')
    .factory('ProfessorApplicationSearch', function ($resource) {
        return $resource('api/_search/professorApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('PrincipleApplicationCheck', function ($resource) {
        return $resource('api/professorApplications/instEmployee/:code', {}, {
            'get':{
                method: 'GET',
                transformResponse: function (data) {
                    if(!data){
                        data={id:0};
                    }
                    data = angular.fromJson(data);

                    return data;
                }
            }
            /*'get': { method: 'GET', isArray: false}*/
        });
    }).factory('PrincipleApplicationList', function ($resource) {
        return $resource('api/professorApplications/professorList/:status', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('PrincipleApplicationSummaryList', function ($resource) {
        return $resource('api/professorApplications/summaryList', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('PrincipleApplicationsByLevel', function ($resource) {
        return $resource('api/professorApplications/applications/:status/:levelId', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('PrincipleApplicationDecline', function ($resource) {
        return $resource('api/professorApplications/decline/:id', {}, {
            'decline': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });

    });
