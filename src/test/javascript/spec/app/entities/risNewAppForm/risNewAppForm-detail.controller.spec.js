'use strict';

describe('RisNewAppForm Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRisNewAppForm, MockDistrict, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRisNewAppForm = jasmine.createSpy('MockRisNewAppForm');
        MockDistrict = jasmine.createSpy('MockDistrict');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'RisNewAppForm': MockRisNewAppForm,
            'District': MockDistrict,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("RisNewAppFormDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:risNewAppFormUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
