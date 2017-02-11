'use strict';

angular.module('stepApp')
    .factory('DlContUpldSearch', function ($resource) {
        return $resource('api/_search/dlContUplds/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })


    .factory('getallbyid', function ($resource) {
        return $resource('api//dlContUplds/findallbyid/:id', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getallbysid', function ($resource) {
        return $resource('api//dlContUplds/findallbysid/:id', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })

    .factory('getallbytype', function ($resource) {
        return $resource('api//dlContUplds/findallbytype/:id', {}, {
            'get': {method: 'GET', isArray: true}
        });
    })
