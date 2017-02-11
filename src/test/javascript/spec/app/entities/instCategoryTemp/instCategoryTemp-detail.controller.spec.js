'use strict';

describe('InstCategoryTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstCategoryTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstCategoryTemp = jasmine.createSpy('MockInstCategoryTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstCategoryTemp': MockInstCategoryTemp
        };
        createController = function() {
            $injector.get('$controller')("InstCategoryTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instCategoryTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
