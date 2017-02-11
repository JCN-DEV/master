'use strict';

angular.module('stepApp')
    .factory('InstEmpSpouseInfoSearch', function ($resource) {
        return $resource('api/_search/instEmpSpouseInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmpSpouseInfoForCurrent', function ($resource) {
        return $resource('api/instEmpSpouseInfo/current/', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
