'use strict';

angular.module('stepApp')
    .factory('MpoApplicationSearch', function ($resource) {
        return $resource('api/_search/mpoApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('MpoApplicationInstitute', function ($resource) {
        return $resource('api/mpoApplications/institute/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationCheck', function ($resource) {
        return $resource('api/mpoApplications/instEmployee/:code', {}, {
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
    }).factory('MpoApplicationCount', function ($resource) {
        return $resource('api/mpoApplications/mpoList/:status', {}, {
            'get':{
            method: 'GET',
                transformResponse: function (data)
                {
                    if(data){
                        data = angular.fromJson(data);
                        data = {size:data.length};
                        data = angular.fromJson(data);
                    }
                    //console.log(data);
                    return data;
                }
            }
            /*'get': { method: 'GET', isArray: false}*/
        });
    }).factory('MpoApplicationList', function ($resource) {
        return $resource('api/mpoApplications/mpoList/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationDecline', function ($resource) {
        return $resource('api/mpoApplications/decline/:id', {}, {
            'decline': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });

    }).factory('MpoApplicationPayScale', function ($resource) {
        return $resource('api/mpoApplications/payScaleList/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });

    })
    .factory('SalaryGenerateByInstitute', function ($resource) {
        return $resource('api/generateSalary', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoSalaryMonthList', function ($resource) {
        return $resource('api/mpoSalaryMonthList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MpoSalaryYearList', function ($resource) {
        return $resource('api/mpoSalaryYearList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('SalaryGeneratedForYearAndMonth', function ($resource) {
        return $resource('api/isSalaryGenerated/:year/:month', {}, {
            'get': { method: 'GET', isArray: false}
        });
     }).factory('SalaryDisburseForYearAndMonth', function ($resource) {
            return $resource('api/disburse/:year/:month', {}, {
                'get': { method: 'GET', isArray: false}
            });
    }).factory('AllForwaringList', function ($resource) {
        return $resource('api/application/approveList/:type', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationFinalList', function ($resource) {
        return $resource('api/mpoApplications/mpoFinalList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationPendingForCommittee', function ($resource) {
        return $resource('api/mpoApplications/committee/pendingApplications/currentLogin', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationSummaryList', function ($resource) {
        return $resource('api/mpoApplications/mpoSummaryList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('MpoApplicationsByLevel', function ($resource) {
        return $resource('api/mpoApplications/mpoList/:status/:levelId', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
