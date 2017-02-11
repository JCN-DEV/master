/*
'use strict';

angular.module('stepApp')
    .factory('CmsTradeSearch', function ($resource) {
        return $resource('api/_search/cmsTrades/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsTradeByCode', function ($resource) {
            return $resource('api/cmsTrades/code/:code', {}, {
                'query': {method: 'GET', isArray: true}
            });
        }).factory('CmsTradesByCurriculum', function ($resource) {
            return $resource('api/cmsTrades/cmsCurriculum/:id', {}, {
                'query': {method: 'GET', isArray: true}
            });
        });
*/


'use strict';

angular.module('stepApp')
    .factory('CmsTradeSearch', function ($resource) {
        return $resource('api/_search/cmsTrades/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('CmsTradeByCode', function ($resource) {
            return $resource('api/cmsTrades/code/:code', {}, {
                'query': {method: 'GET', isArray: true}
            });
    })

    .factory('CmsTradeByName', function ($resource) {
            return $resource('api/cmsTrades/name/:name', {}, {
                'query': {method: 'GET', isArray: true}
            });
    })


    .factory('CmsTradesByCurriculum', function ($resource) {
            return $resource('api/cmsTrades/cmsCurriculum/:id', {}, {
                'query': {method: 'GET', isArray: true}
            });
        })
        .factory('FindActivecmstrades', function ($resource) {
                            return $resource('api/cmsTrades/allActivecmsTrades', {}, {
                                'query': {method: 'GET', isArray: true}
                            });
                        })
        ;
