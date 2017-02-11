'use strict';

angular.module('stepApp')
    .factory('Register', function ($http, $resource) {
        return $resource('api/register', {}, {
            'save'  : { method: 'POST', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    return {id:data};
                }].concat($http.defaults.transformResponse)}
        });
    })
    .factory('RegisterDeo', function ($http, $resource) {
        return $resource('api/registerDeo', {}, {
            'save'  : { method: 'POST', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    return {id:data};
                }].concat($http.defaults.transformResponse)}
        });
    }).factory('RegisterHrm', function ($http, $resource) {
        return $resource('api/registerHrm', {}, {
            'save'  : { method: 'POST', params: {}, format: 'json',
                transformResponse: [function (data, headersGetter) {
                    return {id:data};
                }].concat($http.defaults.transformResponse)}
        });
    });


