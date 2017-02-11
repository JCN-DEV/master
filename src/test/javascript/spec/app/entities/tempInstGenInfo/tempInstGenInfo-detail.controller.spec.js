'use strict';

describe('TempInstGenInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTempInstGenInfo, MockInstituteInfo, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTempInstGenInfo = jasmine.createSpy('MockTempInstGenInfo');
        MockInstituteInfo = jasmine.createSpy('MockInstituteInfo');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TempInstGenInfo': MockTempInstGenInfo,
            'InstituteInfo': MockInstituteInfo,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("TempInstGenInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:tempInstGenInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
