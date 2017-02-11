'use strict';

describe('InstGenInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstGenInfo, MockInstitute, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstGenInfo = jasmine.createSpy('MockInstGenInfo');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstGenInfo': MockInstGenInfo,
            'Institute': MockInstitute,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("InstGenInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instGenInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
