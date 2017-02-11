'use strict';

angular.module('stepApp')
    .factory('IisCourseInfoSearch', function ($resource) {
        return $resource('api/_search/iisCourseInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfosOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfos/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfos/cmsTrades/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesByInstitute', function ($resource) {
        return $resource('api/iisCourseInfos/cmsTrades/institute/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfoOfCurrentInstByTrade', function ($resource) {
        return $resource('api/iisCourseInfos/currentInstitute/trade/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    })
    .factory('IisCourseInfoTempSearch', function ($resource) {
        return $resource('api/_search/iisCourseInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfosTempOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfoTemps/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesTempOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfoTemps/cmsTrades/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesTempByInstitute', function ($resource) {
        return $resource('api/iisCourseInfoTemps/cmsTrades/institute/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoEnlistedIISCourses', function ($resource) {
        return $resource('api/iisCourseInfos/mpoEnlisted', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfoTempOfCurrentInstByTrade', function ($resource) {
        return $resource('api/iisCourseInfoTemps/currentInstitute/trade/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });

/*'use strict';

angular.module('stepApp')
    .factory('IisCourseInfoSearch', function ($resource) {
        return $resource('api/_search/iisCourseInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfosOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfos/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesOfCurrentInst', function ($resource) {
        return $resource('api/iisCourseInfos/cmsTrades/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTradesByInstitute', function ($resource) {
        return $resource('api/iisCourseInfos/cmsTrades/institute/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCourseInfoOfCurrentInstByTrade', function ($resource) {
        return $resource('api/iisCourseInfos/currentInstitute/trade/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });*/
