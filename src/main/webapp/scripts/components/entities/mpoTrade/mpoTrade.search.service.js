'use strict';

angular.module('stepApp')
    .factory('MpoTradeSearch', function ($resource) {
        return $resource('api/_search/mpoTrades/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('MpoTradeByTradeId', function ($resource) {
        return $resource('api/mpoTrades/cmsTrades/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('MpoCmsTradeByCurriculumId', function ($resource) {
        return $resource('api/mpoTrades/cmsTrades/cmsCurriculum/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
